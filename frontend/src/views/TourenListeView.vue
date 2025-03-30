<template>

  <TourenListe :touren="touren"></TourenListe>

</template>


<script setup lang="ts">
import type { ITourDTD } from '@/stores/ITourDTD'
import TourenListe from './../components/tour/TourenListe.vue'
import { ref } from 'vue'

const touren = ref<ITourDTD[]>([
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
])

</script>

