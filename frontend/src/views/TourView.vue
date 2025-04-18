<template>
    <div style="display: flex; justify-content: space-between;">
      <div>
      <h1 v-if="tour">Tour {{ tour.id }} : {{ tour.startOrtName }} -> {{ tour.zielOrtName }}</h1>

      <p v-if="tour">{{ tour.info || 'Keine zusätzliche Info verfügbar' }} </p>
      <p v-if="tour">Du fährst mit {{tour.anbieterName}}.</p>

      <p v-if="tour">Von: {{ tour.startOrtName }} -> {{ tour.zielOrtName }} ({{tour.distanz.toFixed(0)}} km)</p>
      <p v-if="tour">Preis: {{ tour.preis }} €</p>
      <p v-if="tour">Plaetze: {{tour.plaetze}} davon gebucht: {{tour.buchungen}}, also frei: {{frei}}</p>

    </div>
    <div style="padding-right: 20em;">
      <p>Gebucht von:</p>

      <div v-if="tour" v-for="name in tour.mitFahrGaesteNamen" :key="name">
        <p>{{ name }}</p>
      </div>
      <button v-if="tour" @click="entfernen(tour)">Entfernen</button>
      <!-- <div v-if="tour" v-for="n in tour.plaetze" :key="n">
        <div :class="n <= tour.buchungen ? 'belegt' : 'frei'">{{ n }}</div>
      </div> -->
    </div>
    </div>
    <p v-if="!tour">Tour nicht gefunden.</p>
      <button v-if="tour" @click="buchen(tour)">Buchen</button>
  </template>
  
  <script setup lang="ts">
  import { defineProps, computed } from 'vue'
  import { useRoute } from 'vue-router'
  import { useTourenStore } from '@/stores/tourenstore'
  import { useInfo } from './../composables/useInfo.ts'
  import type { ITourDTD } from '@/stores/ITourDTD.ts'
  
  const { setzeInfo } = useInfo()
  const route = useRoute()
  //console.log(route)
  
  const { updateTourListe, tourBuchen, tourdata, nutzerAusTourEntfernen } = useTourenStore() //state nicht importierbar, da state nur intern des Stores verwaltet. 
  // Statt dessen kann man funtkionen importieren, mit denen sich state manipulieren kässt.
  updateTourListe() //Daten werden erst angezeig, wenn man ein update durchführt. 
  
  const tourid = computed(() => Number(route.params.tourid)) //route Params enthält die übergebenen Parameter(${tourid})
  const tour = computed(() => tourdata.tourliste.find(t => t.id === tourid.value)) 
  const frei = computed(() => { if(!tour.value) return 0; return tour.value.plaetze - tour.value.buchungen})

  function buchen(tour : ITourDTD){
    tourBuchen(tour)
  }

  function entfernen(tour: ITourDTD){
    nutzerAusTourEntfernen(tour)
  }

if(tour.value && tour.value.distanz > 300){ //ich muss erst mit tour.value prüfen, ob tour ein Objekt doer undefined zurückgibt, bevor ich auf entfernung zugreife
    setzeInfo("Die Entfernung beträgt über 300 km.")
}

  </script>
  