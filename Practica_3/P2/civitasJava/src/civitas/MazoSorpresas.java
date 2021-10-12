/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/** @author Marina Muñoz Cano y Mario López González */
public class MazoSorpresas {

  private Boolean barajada;
  private int usadas;
  private Boolean debug;
  private Sorpresa ultimaSorpresa;

  // Atributos de referencia
  private ArrayList<Sorpresa> cartasEspeciales;
  private ArrayList<Sorpresa> sorpresas;

  /*
    Método init
      Efecto:
        Inicializa los atributos sorpresas, cartasEspeciales, barajadas y usadas.
  */
  private void init() {
    sorpresas = new ArrayList();
    cartasEspeciales = new ArrayList();
    barajada = false;
    usadas = 0;
  }

  /*
   Constructor con Parametros
  */
  MazoSorpresas(Boolean d) {
    debug = d;
    init();
    if (debug) Diario.getInstance().ocurreEvento("Mazo Sorpresas en modo Debug");
    else Diario.getInstance().ocurreEvento("Mazo Sorpresas en modo no Debug");
  }

  /*
    Constructor sin Parametros
  */
  MazoSorpresas() {
    init();
    debug = false;
  }

  /*
    Metodo al Mazo
      Parametros:
        s: nueva carta sorpresa.
      Efecto:
        Si el mazo no ha sido barajado se añade la carta s al mazo.
  */
  void alMazo(Sorpresa s) {
    if (!barajada) sorpresas.add(s);
  }

  /*
    Metodo Siguiente
      Devuelve:
        La siguiente carta de sorpresa del mazo
      Efecto:
        Baraja el mazo si no se ha hecho previamente, o si se ha llegado al final del mismo.
        Guarda la carta de sorpresa siguiente en la variable ultimaSorpresa y la pone al final del mazo.
        Devuelve la carta que se ha guardado en ultimaSorpresa
  */

  Sorpresa siguiente() {
    if (!barajada || usadas == sorpresas.size()) {
      if (!debug) Collections.shuffle(sorpresas);
      usadas = 0;
      barajada = true;
    }
    usadas++;
    ultimaSorpresa = sorpresas.get(0);

    for (int i = 0; i < sorpresas.size() - 1; i++) sorpresas.set(i, sorpresas.get(i + 1));

    sorpresas.set(sorpresas.size() - 1, ultimaSorpresa);

    return ultimaSorpresa;
  }

  /*
    Metodo inhabilitarCartaEspecial
      Parametros:
        sorpresa: carta sorpresa a inhabilitar
      Efecto:
        Si la carta sorpresa se encuentra en el mazo de sorpresas la quita y la
        añade a cartasEspeciales, dejando constancia en el diario.
        En caso contrario no hace nada.
  */

  void inhabilitarCartaEspecial(Sorpresa sorpresa) {

    if (sorpresas.contains(sorpresa)) {
      sorpresas.remove(sorpresa);
      cartasEspeciales.add(sorpresa);
      Diario.getInstance().ocurreEvento("Carta Sorpresa retirada del mazo");
    }
  }

  /*
    Metodo habilitarCartaEspecial
      Parametros:
        sorpresa: carta sorpresa que se va a habilitar
      Efecto:
        Si la carta sorpresa se encuentra en el mazo cartasEspeciales la quita y la
        añade a sorpresas, dejando constancia en el diario.
        En caso contrario no hace nada.
  */

  void habilitarCartaEspecial(Sorpresa sorpresa) {

    if (cartasEspeciales.contains(sorpresa)) {
      cartasEspeciales.remove(sorpresa);
      sorpresas.add(sorpresa);
      Diario.getInstance().ocurreEvento("Carta Sorpresa introducida en el mazo");
    }
  }

  @Override
  public String toString() {
    return "MazoSorpresas{"
        + "sorpresas="
        + sorpresas
        + ", barajada="
        + barajada
        + ", usadas="
        + usadas
        + ", debug="
        + debug
        + ", cartasEspeciales="
        + cartasEspeciales
        + ", ultimaSorpresa="
        + ultimaSorpresa
        + '}';
  }
}
