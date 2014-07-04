#[RES](http://redditenhancementsuite.com/) Backup Assistant

##Browser/OS Status (as of V0.3.1)
####Mac (Tested on 10.9.4)
* Firefox - Working
* Chrome - Working
* Chromium - Working
* Opera - Working
* Safari - Working

####Linux (Tested on Ubuntu)
* Firefox - Working
* Chromium - Working
* Chrome - Working
* Opera - Working

####Windows
* Chrome - Working (Big thanks to [/u/Trapt45](http://www.reddit.com/user/Trapt45) for testing)

-------------------------

###TODO (as of V0.3.1)
* Fix behavior when the RES-Backups folder already exists in the users home dir. As of right now you will be prompted to rename it to RES-Backups-OLD.

* Find out more about the .localstorage-journal file and its purpose (and then if it should be backed-up as well).

* Add settings section to application

* Add check for update feature

----------------------

#####What: 
*   The application searches for RES (Reddit Ehancement Suite) installs on your computer
    and make a copy of them in a folder in your user's home directory.

#####Why: 
*   Backups are good, and especially right now for firefox users because firefox has been deleting res settings on reset.

#####Who:
* Anyone who uses RES, and has a Java Runtime on their machine can use this application.

#####How:
*   The application works by checking the location of many different locations different browsers use to store your RES settings.  Then if
the file is found it is added to a backup file which is stored at the location you specify.

* To run it simply download the latest [release](https://github.com/walshie4/backup-RES-Settings/releases) and double click it. 
Then click `Detect RES Installs`. Then check the installs you want to make a backup of and click `Make Backup`. 
A copy of the selected config files will be stored in your user's home directory in a folder called RES-Backups.

#####Where:
*   Check the releases for the latest working version, or download the latest commit and build from source (but don't expect it to be bug-less if you do).

