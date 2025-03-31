import { ref } from 'vue'

const info = ref<string>('') //Konstante referenz, aber der Wert der referenz ist Variabel(info zeigt auf ref(Referenzbjekt, kan nicht neu zugewiesen werden)). 
// var ist außerhalb eines Blocks verfügbar, let und const nur innerhalb.

export function useInfo(){
    return {
        info: info as Readonly<typeof info>, //nur lesbar und vom typ ref<string>
        loescheInfo: () => {info.value = ''}, //lambda-Ausdruck
        setzeInfo: (msg: string) => {info.value = msg},
    }
}