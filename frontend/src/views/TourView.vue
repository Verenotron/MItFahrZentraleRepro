<template>
    <div style="display: flex; justify-content: space-between;">
      <div>
      <h1 v-if="tour">{{ $t('tour')}} {{ tour.id }} : {{ tour.startOrtName }} -> {{ tour.zielOrtName }}</h1>

      <p v-if="tour">{{ tour.info || $t('tour') }} </p>
      <p v-if="tour">{{ $t('duFaehrstMit') }} {{tour.anbieterName}}.</p>

      <p v-if="tour">{{ $t('von') }}: {{ tour.startOrtName }} -> {{ tour.zielOrtName }} ({{tour.distanz.toFixed(0)}} km)</p>
      <p v-if="tour">{{ $t('preis') }}: {{ tour.preis }} €</p>
      <p v-if="tour">{{ $t('plaetze') }}: {{tour.plaetze}} {{ $t('davonGebucht') }}: {{tour.buchungen}}, {{ $t('alsoFrei') }}: {{frei}}</p>

    </div>
    <div style="padding-right: 20em;">
      <p>{{ $t('gebuchtVon') }}:</p>

      <div v-if="tour" v-for="name in tour.mitFahrGaesteNamen" :key="name">
        <p>{{ name }}</p>
      </div>
      <button v-if="tour" @click="entfernen(tour)">{{ $t('entfernen') }}</button>
      <!-- <div v-if="tour" v-for="n in tour.plaetze" :key="n">
        <div :class="n <= tour.buchungen ? 'belegt' : 'frei'">{{ n }}</div>
      </div> -->
    </div>
    </div>
    <p v-if="!tour">{{ $t('tourNichtGefunden') }}.</p>
      <button v-if="tour" @click="buchen(tour)">{{ $t('buchen') }}</button>
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
  