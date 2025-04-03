package com.WebProjekt.MItfahrZentrale.messaging;

public class FrontendNachrichtEvent {
  private Typ typ;
  private long id;
  private Action action;

  public enum Typ {
      TOUR, 
      ORT,
      BENUTZER;
  }

  public enum Action {
      CREATE,
      UPDATE,
      DELETE;
  }

  public FrontendNachrichtEvent(Typ typ, long id, Action art) {
      this.typ = typ;
      this.id = id;
      this.action = art;
  }

  public Typ getTyp() {
      return typ;
  }

  public void setTyp(Typ typ) {
      this.typ = typ;
  }

  public long getId() {
      return id;
  }

  public void setId(long id) {
      this.id = id;
  }

  public Action getAction() {
      return action;
  }

  public void setAction(Action art) {
      this.action = art;
  }

}