import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
      proxy: {'/api': 'http://localhost:8080', //proxy ist ein Vermittler von Anfragen. (Vite ist localhost:3000)
        //alle Anfragen/URLs, die mit /api beginnen, werden vom Vite-Entwicklungsserver an localhost:8080/api weitergeleitet.
      '/stompbroker': { //Ähnlich wie proxy, jedoch eine Websocket-Verbindung. Anfragen vom Stompbroker werden an Server unter localhost:8080 weitergeleitet, aber sie verwenden das Websocket-Protokoll.
      target: 'http://localhost:8080/', ws: true //ws : true ->
      }
    }
  },

  //Vite ist ein Entwicklungsserver, der dafür sorgt, dass Web-Apps in entwicklungsumgebung schnell geladen und aktualisiert wird.
  //Kümmert sich um Bundling und Bereitstellung der App.
  //Vte stellt sicher, dass statische Ressourcen wie HTML, CSS und JavaScript während Entwicklung korrekt bedient werden.
  //WebSockets ermöglichen Echtzeit-Kommunikation zwischen Frontend und Backend.
  //CORD-Probleme: Sicherheitsproblem, dass der Webbrowser Anfragen von einer Domain nicht an eine adnere Domain gesendet werden, die nicht explitzit erlaubt wurden. 
})
