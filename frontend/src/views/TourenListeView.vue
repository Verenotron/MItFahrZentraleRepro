<template>

  <TourenListe :touren="touren"></TourenListe>

</template>


<script setup lang="ts">
import type { ITourDTD } from '@/stores/ITourDTD'
import TourenListe from './../components/tour/TourenListe.vue'
import { ref } from 'vue'

const touren = ref<ITourDTD[]>([
  { id: 1, name: 'Stadtführung', description: 'Erkunden Sie die schönsten Ecken der Stadt.', price: 25 },
  { id: 2, name: 'Bergwanderung', description: 'Wandern Sie durch die Alpen mit einem erfahrenen Guide.', price: 45 },
  { id: 3, name: 'Bootstour', description: 'Genießen Sie eine entspannte Fahrt auf dem See.', price: 30 }
])

//defineProps<{ touren: ITourDTD[] }>()

//export default touren

// export default {
//   name: 'TourenListeView',

//   // Interface-Definition für ITourDTD
//   data() {
//     return {
//       touren: ITourDTD = [
//         { id: 1, name: 'Stadtführung', description: 'Erkunden Sie die schönsten Ecken der Stadt.', price: 25 },
//         { id: 2, name: 'Bergwanderung', description: 'Wandern Sie durch die Alpen mit einem erfahrenen Guide.', price: 45 },
//         { id: 3, name: 'Bootstour', description: 'Genießen Sie eine entspannte Fahrt auf dem See.', price: 30 }
//       ]
//     }
//   }
// }

</script>
