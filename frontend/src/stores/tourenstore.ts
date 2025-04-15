import { defineStore } from 'pinia'
import type { ITourDTD } from './ITourDTD'
import { Client, type Message } from '@stomp/stompjs';
import type { IFrontendNachrichtEvent } from '@/services/IFrontendNachrichtEvent';
import { useInfo } from './../composables/useInfo.ts'
import { useLogin } from '@/composables/useLogin.ts'

const { loginState } = useLogin()

export const useTourenStore = defineStore('useTourenStore', { //Definiere store als tourenstore
    state: () => ({ //state enthält reaktive Daten, die innerhalb des Stores verwaltet werden.
        tourdata: { //tourdata ist reaktives Objekt, das ok und tourliste bereitstellt. Soblad sich ok und tourListe ändern, ändert sich tourdata ud damit die UI
            ok: true,
            tourliste: [] as Array<ITourDTD>
            // tourliste: [] as ITourDTD[],
            // ok: false
        },
        stompClient: null as Client | null 
    }),
    actions:{ //actions enthält Funktionen, die state manipulieren oder Daten ändern. (hier befüllt action tourliste mit Daten.)
        async updateTourListe(){

          try{
            const response = await fetch('http://localhost:8080/api/tour', {headers: {'Authorization' : `Bearer ${loginState.jwt}`}})
            if(!response.ok){
              throw new Error('Fehler beim Laden der Tourdaten')
            }
            const data: ITourDTD[] = await response.json()
            this.tourdata.tourliste = data
            this.tourdata.ok = true
          }catch(error){
            console.error('Fehler beim Abrufen der Tourdaten', error)
            this.tourdata.ok = false
          }

        },
        startTourLiveUpdate(){
          if(this.stompClient){
            console.log('STOMP-Client existiert bereits. Verbindung wird wiederverwendet.')
            return;
          }

          this.stompClient = new Client({
            brokerURL: 'ws://localhost:8080/stompbroker',
          })
          this.stompClient.activate(); //verbindung aufbauen
          const{ setzeInfo, loescheInfo } = useInfo()
          this.stompClient.onWebSocketError = ( event ) => {console.error("WebSocket Fehler:", event); setzeInfo("Backend kann nciht erreicht werden.")};
          this.stompClient.onStompError = (frame) => {console.error("STOMP Fehler: ", frame);};
          this.stompClient.onConnect = (frame) => {
            console.log('Verbunden mit STOMP-Server');
            this.stompClient?.subscribe("/topic/tour", (message: Message)=> {
              const nachricht: IFrontendNachrichtEvent = JSON.parse(message.body);
              console.log('Empfangene Nachricht: ', message.body);
              //if (nachricht.typ === "TOUR") {
                this.updateTourListe(); // Updatet die Tour-Liste bei einer TOUR-Nachricht
            //}
            });
          };
          loescheInfo()
          this.stompClient.onDisconnect = () => {  console.log('Verbindung getrennt');};
          //this.stompClient.activate(); //muss weg bleiben, hatte die ganze zeit versucht doppelt auf port 8080 zuzugreifen!!!!!
          
        },

      // updateTourListe(){
        //     this.tourdata.tourliste = [
        //       { 
        //         id: 1, 
        //         abfahrDateTime: '2025-04-10T08:00', 
        //         preis: 29, 
        //         plaetze: 20, 
        //         buchungen: 15, 
        //         startOrtName: 'Berlin', 
        //         startOrtId: 101, 
        //         zielOrtName: 'München', 
        //         zielOrtId: 102, 
        //         anbieterName: 'Anbieter A', 
        //         anbieterId: 1001, 
        //         distanz: 585, 
        //         info: 'Keine Info'
        //       },
        //       { 
        //         id: 2, 
        //         abfahrDateTime: '2025-04-11T09:30', 
        //         preis: 35, 
        //         plaetze: 25, 
        //         buchungen: 10, 
        //         startOrtName: 'Hamburg', 
        //         startOrtId: 201, 
        //         zielOrtName: 'Köln', 
        //         zielOrtId: 202, 
        //         anbieterName: 'Anbieter B', 
        //         anbieterId: 1002, 
        //         distanz: 360, 
        //         info: 'Keine Info'
        //       },
        //       { 
        //         id: 3, 
        //         abfahrDateTime: '2025-04-12T07:45', 
        //         preis: 22, 
        //         plaetze: 18, 
        //         buchungen: 5, 
        //         startOrtName: 'Frankfurt', 
        //         startOrtId: 301, 
        //         zielOrtName: 'Stuttgart', 
        //         zielOrtId: 302, 
        //         anbieterName: 'Anbieter C', 
        //         anbieterId: 1003, 
        //         distanz: 210, 
        //         info: 'Keine Info'
        //       }
        //     ]
            
        // }
    }
})