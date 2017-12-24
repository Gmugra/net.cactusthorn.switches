package net.cactusthorn.switches.rules;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.xml.bind.JAXBException;

import net.cactusthorn.switches.SwitchParameter;
import net.cactusthorn.switches.SwitchesXMLLoader;

//must be final, because can start the thread in the constructor
public final class WatchSwitches implements Runnable, Switches {
	
	private final WatchService watchService = FileSystems.getDefault().newWatchService();
	private final Thread thread;
	private final Path xmlPath;
	private long lastTimeStamp;
	private final Path parentDirecctory;
	private final SwitchesXMLLoader loader;
	private final AbstractSwitches switches;
	
	public WatchSwitches(Path xmlPath, SwitchesXMLLoader loader) throws IOException, JAXBException {
		
		this.xmlPath = xmlPath;
		this.lastTimeStamp = xmlPath.toFile().lastModified();
		
		parentDirecctory = this.xmlPath.getParent();
		parentDirecctory.register(watchService, ENTRY_CREATE, ENTRY_MODIFY);
		
		this.loader = loader;
		
		switches = loader.load(xmlPath);
		
		thread = new Thread(this);
		thread.setName("WatchSwitches:" + thread.getName());
		thread.start();
	}
	
	@Override public boolean exists(String switchName) {
		return switches.exists(switchName);
	}

	@Override public boolean turnedOn(String switchName, SwitchParameter<?>... parameters) {
		return switches.turnedOn(switchName, parameters);
	}

	public void interrupt() {
		thread.interrupt();
	}
	
	@Override public void run() {
		while (true) {

			// wait for key to be signaled
			WatchKey key;
			try {
				key = watchService.take();
			} catch (InterruptedException ie) {
				return;
			}
			
			key.pollEvents().stream().filter(this::checkWatchEvent).filter(this::checkFile).forEach(this::reload);

			// reset the key - this step is critical if you want to receive further watch events. 
			// If the key is no longer valid, the directory is inaccessible so exit the loop.             
			if (!key.reset()) {
				return;
			}
		}
	}
	
	private boolean checkWatchEvent(WatchEvent<?> event) {
		
		WatchEvent.Kind<?> kind = event.kind();
		
		// This key is registered only for ENTRY_CREATE & ENTRY_MODIFY events,
		// but an OVERFLOW event can occur regardless if events are lost or discarded.
		return !OVERFLOW.equals(kind) && (ENTRY_CREATE.equals(kind) || ENTRY_MODIFY.equals(kind));
	}
	
	private boolean checkFile(WatchEvent<?> event) {
		
		String fileName = String.valueOf(((Path)event.context()).getFileName());
		
		//some temporary files(e.g. rsync) started from ".", -> ignore them
		if (fileName.indexOf('.') == 0 || !fileName.endsWith(".xml") ) return false;
	
		//is it directory or not, size and lastModified we will know only after resolve
		Path resolvedPath = parentDirecctory.resolve((Path) event.context());
		File resolvedFile = resolvedPath.toFile();
		
		if (resolvedFile.isDirectory() ) return false;
		
		//File with 0(zero) size. Empty file, YES.
		//Events according such files(not directories!) sometimes happens.
		//May be reason is Windows?
		//Anyway - such files must be ignored.
		if (resolvedFile.length() == 0) return false;

		if (!xmlPath.equals(resolvedPath) ) return false;
		
		//multiple event because of same file change happens sometimes
		//lets check that lastModified is new to avoid unnecessary work 		
		long newTime = resolvedFile.lastModified();
		if (newTime != lastTimeStamp ) {
			lastTimeStamp = newTime;
		} else {
			return false;
		}

		return true;
	}
	
	private void reload(WatchEvent<?> event) {
		
		AbstractSwitches newSwitches;
		try {
			newSwitches = loader.load(xmlPath);
		} catch (JAXBException | IOException e ) {
			e.printStackTrace();
			return;
		}
		
		switches.updateBy(newSwitches);
	}
}
