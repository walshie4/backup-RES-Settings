#!/usr/bin/env bash
#Written on 2/10/14
#Written by: Adam Walsh
#This only has been tested on Mac OS X

#Safari is not supported :(   (yet)
#SAFARI="~/Library/Safari/LocalStorage/safari-extension_com.honestbleeps.redditenhancementsuite-<10 char alphanumeric string>_0.localstorage"

#CONFIG
USER_HOME=$(eval echo ~${SUDO_USER})
PATH_TO_BACKUP="${USER_HOME}/Documents"
#END CONFIG

declare -a found=()

backups="$PATH_TO_BACKUP/Backups"
mkdir "$backups"

if [[ -f ~/Library/Application\ Support/Chromium/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage ]]; then
    found+=("Chromium")
    cp ~/Library/Application\ Support/Chromium/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage $backups
fi
if [[ -f ~/Library/Application\ Support/Google/Chrome/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage ]]; then
    found+=("Chrome")
    cp ~/Library/Application\ Support/Google/Chrome/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage $backups
fi
#if [[ -f "$SAFARI" ]]; then
#    found+="Safari"
#fi
if [[ -f ~/Library/Mozilla/Firefox/Profiles/ohy29rj0.default/jetpack/jid1-xUfzOsOFlzSOXg\@jetpack/simple-storage/store.json ]]; then
    found+=("Firefox")
    cp ~/Library/Mozilla/Firefox/Profiles/ohy29rj0.default/jetpack/jid1-xUfzOsOFlzSOXg\@jetpack/simple-storage/store.json $backups
fi
if [[ -f ~/Library/Application\ Support/Firefox/Profiles/517bszfu.default/jetpack/jid1-xUfzOsOFlzSOXg@jetpack/simple-storage/store.json ]]; then
    found+=("Firefox (Alternate Location)")
    cp ~/Library/Application\ Support/Firefox/Profiles/517bszfu.default/jetpack/jid1-xUfzOsOFlzSOXg@jetpack/simple-storage/store.json $backups
fi

for i in "${found[@]}"; do
    echo "RES settings file found for $i browser"
done

zip -r "$PATH_TO_BACKUP/RES_backup.zip" $backups
rm -R "$backups"
