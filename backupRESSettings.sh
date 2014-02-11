#!/usr/bin/env bash
#Written on 2/10/14
#Written by: Adam Walsh
#This only has been tested on Mac OS X


CHROMIUM="~/Library/Application\ Support/Chromium/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage"
CHROME="~/Library/Application\ Support/Google/Chrome/Default/Local\ Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage"
#SAFARI="~/Library/Safari/LocalStorage/safari-extension_com.honestbleeps.redditenhancementsuite-<10 char alphanumeric string>_0.localstorage"
FIREFOX="~/Library/Mozilla/Firefox/Profiles/ohy29rj0.default/jetpack/jid1-xUfzOsOFlzSOXg\@jetpack/simple-storage/store.json"
FIREFOX2="~/Library/Application\ Support/Firefox/Profiles/ohy29rj0.default/jetpack/jid1-xUfzOsOFlzSOXg\@jetpack/simple-storage/store.json"

declare -a found=()

if [[ -f "$CHROMIUM" ]]; then
    echo chromium
    found += "Chromium"
fi
if [[ -f "$CHROME" ]]; then
    echo chrome
    found += "Chrome"
fi
#if [[ -f "$SAFARI" ]]; then
#    echo safari
#    found += "Safari"
#fi
if [[ -f "$FIREFOX" ]]; then
    echo firefox
    found += "Firefox"
fi
if [[ -f "$FIREFOX2" ]]; then
    echo firefox2
    found += "Firefox (Alternate Location)"
fi

for i in ${found[@]}; do
    echo "RES settings file found for $i browser"
done
