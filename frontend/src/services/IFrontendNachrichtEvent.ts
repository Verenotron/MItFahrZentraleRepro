export interface IFrontendNachrichtEvent { 
    //In Type-script Interfaces sind keine Getter und Setter nötig.
    //Interface definiert nur die Struktur der Daten.
    typ: string;
    id: number;
    operation: string; 
  }