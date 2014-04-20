dist: RESBackup.class RESBackupGUI.class
	jar cvfm RES-Backup-Assistant.jar MANIFEST.MF *.class

RESBackup.class:
	javac RESBackup.java

RESBackupGUI.class:
	javac RESBackupGUI.java

clean:
	rm -f *.class
