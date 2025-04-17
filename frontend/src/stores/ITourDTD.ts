export interface ITourDTD{
    id: number
    abfahrDateTime: String 
    preis: number 
    plaetze: number
    buchungen: number
    startOrtName: String 
    startOrtId: number
    zielOrtName: String 
    zielOrtId: number
    anbieterName: String 
    anbieterId: number 
    distanz: number
    info: String
    mitFahrGaesteNamen: string[]
}