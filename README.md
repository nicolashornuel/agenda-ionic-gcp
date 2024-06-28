# agenda-ionic-gcp


```sh
ionic start agenda tabs
cd ./agenda
ionic serve
```

- Run ionic capacitor add to add a native iOS or Android project using Capacitor
- Generate your app icon and splash screens using cordova-res --skip-config --copy
- Explore the Ionic docs for components, tutorials, and more: https://ion.link/docs
- https://ionicframework.com/docs/developing/android


```sh
ionic capacitor add android
npm install @capacitor/geolocation
```
- https://ionicframework.com/docs/native/geolocation

debug android
https://stackoverflow.com/questions/71366286/how-to-mount-dev-bus-usb-on-vscodes-devcontainer-for-docker
```sh
adb devices
```

“runArgs”: [ “ — privileged”,
“-v”,
“/dev/bus/usb:/dev/bus/usb” ],