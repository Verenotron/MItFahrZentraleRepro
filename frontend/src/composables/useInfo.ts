import { ref } from 'vue' //ref ist eine Funktion, die reaktive Variablen erstellt. Änderungen in diesen reaktiven Variablen werden automatisch in der UI dargestellt.

const info = ref<string>('') //Konstante referenz, aber der Wert der referenz ist Variabel(info zeigt auf ref(Referenzbjekt, kan nicht neu zugewiesen werden)). 
// var ist außerhalb eines Blocks verfügbar, let und const nur innerhalb.

export function useInfo(){
    return {
        info: info as Readonly<typeof info>, //nur lesbar und vom typ ref<string>. Mcht reaktive Variable Schreibgeschützt.
        loescheInfo: () => {info.value = ''}, //lambda-Ausdruck
        setzeInfo: (msg: string) => {info.value = msg},
        //Schreibschutz von info greift nur außerhalb des Composables. Innerhalb des Composables bleibt info veränderbar.
    }
}