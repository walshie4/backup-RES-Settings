dist: RESBackup.class RESBackupGUI.class
	jar cvfm RES-Backup-Assistant.jar MANIFEST.MF *.class

RESBackup.class:
	javac RESBackup.java

RESBackupGUI.class:
	javac RESBackupGUI.java

test: RESBackup.class RESBackupGUI.class
	java RESBackupGUI

clean:
	rm -f *.class
