package com.dma.testruleta

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import kotlin.random.Random


class RuletaFragment : Fragment() {

    private lateinit var ruletaView: ImageView
    //Listado de premios de la ruleta
    private var premios = listOf("50","75","25","50","pierde turno","75","50","25","75","25","quiebra","25","50","75","100","25","pierde turno","100","150","50","75","150","quiebra","200")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ruleta, container, false)

        // Referencia a la ruleta
        ruletaView = view.findViewById(R.id.ruletaView)

        // Listener para girar la ruleta al pulsarla
        ruletaView.setOnClickListener {
            girarRuleta()
        }

        return view
    }

    /**
     * Se genera el giro de la ruleta y la animacion, tambien muestra el premio ganado
     */
    private fun girarRuleta() {

        // Aleatorio para el giro de la ruleta (minimo 2 vueltas (720) - maximo 5 vueltas 1800)
        val anguloAleatorio = Random.nextDouble(720.0,1800.0).toFloat()

        //Animacion de la ruleta
        val animator = ObjectAnimator.ofFloat(ruletaView, "rotation", anguloAleatorio)
        animator.duration = 3000 //Duración de la animación
        animator.start()
        //Se ejecuta cuando termina la animacion
        animator.doOnEnd {
            // Se obtiene el ángulo actual de la ruleta y se normaliza para que este entre 0 y 360 grados
            val anguloActual = (ruletaView.rotation % 360 + 360) % 360

            //Calcular premio y mostrarlo
            val premio = calcularPremioDesdeAngulo(anguloActual)
            Toast.makeText(requireContext(), premio, Toast.LENGTH_SHORT).show()
            //Se le asigna el angulo actual a la ruleta
            ruletaView.rotation = anguloActual
        }

    }

    /**
     * Calcula el premio segun el angulo de la ruleta
     * @return devuelve el premio
     */
    private fun calcularPremioDesdeAngulo(angulo: Float): String {

        val totalPremios = premios.size
        val gradosPorPremio = 360f / totalPremios //Se divide los grados por premio

        //Calcula el índice del premio
        val indicePremio = (angulo / gradosPorPremio).toInt()

        return premios[indicePremio]
    }


}
