import { ref } from 'vue'

const info = ref<string>('')

export function useInfo(){
    return {
        info: info as Readonly<typeof info>,
        loescheInfo: () => {info.value = ''},
        setzeInfo: (msg: string) => {info.value = msg},
    }
}