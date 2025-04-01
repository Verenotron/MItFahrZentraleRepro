import { defineStore } from 'pinia'
import type { ITourDTD } from './ITourDTD'

export const useTourenStore = defineStore('useTourenStore', { //Definiere store als tourenstore
    state: () => ({ //state enthält reaktive Daten, die innerhalb des Stores verwaltet werden.
        tourdata: { //tourdata ist reaktives Objekt, das ok und tourliste bereitstellt. Soblad sich ok und tourListe ändern, ändert sich tourdata ud damit die UI
            ok: true,
            tourliste: [] as Array<ITourDTD>
        }
    }),
    actions:{ //actions enthält Funktionen, die state manipulieren oder Daten ändern. (hier befüllt action tourliste mit Daten.)
        updateTourListe(){
            this.tourdata.tourliste = [
                { 
                  id: 1, 
                  abfahrt: '2025-04-10 08:00', 
                  von: 'Berlin', 
                  nach: 'München', 
                  entfernung: 585, 
                  plaetze: 20, 
                  buchungen: 15,
                  preis: 29
                },
                { 
                  id: 2, 
                  abfahrt: '2025-04-11 09:30', 
                  von: 'Hamburg', 
                  nach: 'Köln', 
                  entfernung: 360, 
                  plaetze: 25, 
                  buchungen: 10, 
                  preis: 35 
                },
                { 
                  id: 3, 
                  abfahrt: '2025-04-12 07:45', 
                  von: 'Frankfurt', 
                  nach: 'Stuttgart', 
                  entfernung: 210, 
                  plaetze: 18, 
                  buchungen: 5, 
                  preis: 22 
                }
              ]
        }
    }
})