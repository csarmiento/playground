package co.runtime.clustering;

import java.util.List;

public class Recursiones {

    public static int posibilidadesCambio(int plata, List<Integer> monedas, int conteo) {
        if (plata < 0) {
            return conteo;
        } else if (monedas.isEmpty()) {
            if (plata == 0) {
                return conteo + 1;
            } else {
                return conteo;
            }
        } else {
            return posibilidadesCambio(plata, monedas.subList(1, monedas.size()), conteo) +
                    posibilidadesCambio(plata - monedas.get(0), monedas, conteo);
        }
    }
}
