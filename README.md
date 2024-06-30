# agenda-ionic-gcp


```sh
ionic init agenda --type=angular
ionic serve
ionic build
```

- Run ionic capacitor add to add a native iOS or Android project using Capacitor
- Generate your app icon and splash screens using cordova-res --skip-config --copy
- Explore the Ionic docs for components, tutorials, and more: https://ion.link/docs
- https://ionicframework.com/docs/developing/android


## capacitor

initialisation (création d'un fichier capacitor.config.json)
```sh
npx cap init
```

ajouter une plateforme (ios, android, electron)
```sh
npx cap add android
```

ouvrir le projet dans android studio
```sh
npx cap open android
```

le projet peut être synchronisé par plateforme
```sh
npx cap copy android
```
https://capacitorjs.com/docs/android
https://developer.android.com/studio/run/device?hl=fr
```sh
npx cap sync ( npx cap copy && npx cap update )
npx cap run android ( sync, build and deploy )
```

## live reload
ajouter l'ip de la machine pour live reload dans le fichier capacitor.config.json

"server": {
  "url": "http://192.168.1.68:8100",
  "cleartext": true
},
```sh
ionic serve --external 
npx cap open android
```
## geolocation
- https://ionicframework.com/docs/native/geolocation
- https://medium.com/enappd/use-geolocation-geocoding-and-reverse-geocoding-in-ionic-capacitor-b494264f0e85

```sh
npm install @capacitor/geolocation
```

## send position to firebase
```sh
npm install @angular/fire
```

## adb
debug android
https://stackoverflow.com/questions/71366286/how-to-mount-dev-bus-usb-on-vscodes-devcontainer-for-docker
https://help.arborxr.com/en/articles/9435953-how-to-use-adb-commands-in-macos-terminal
```sh
adb devices
```

“runArgs”: [ “ — privileged”,
“-v”,
“/dev/bus/usb:/dev/bus/usb” ],
