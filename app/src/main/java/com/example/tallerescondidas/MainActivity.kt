
package com.example.tallerescondidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tallerescondidas.logic.GameLogic
import com.example.tallerescondidas.sensors.OrientationProvider
import com.example.tallerescondidas.ui.theme.*
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset

class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientationProvider = OrientationProvider(this)

        setContent {
            TallerEscondidasTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FondoApp)
                ) {
                    PantallaPrincipal(orientationProvider)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        orientationProvider.start()
    }

    override fun onPause() {
        super.onPause()
        orientationProvider.stop()
    }
}

fun formatoTiempo(segundos: Int): String {
    val minutos = segundos / 60
    val segs = segundos % 60
    return "%02d:%02d".format(minutos, segs)
}

// Convierte un ángulo de 0 a 359 grados al punto cardinal más cercano.
fun cardinalDe(angulo: Float): String {
    val a = ((angulo % 360) + 360) % 360
    return when {
        a < 22.5 || a >= 337.5 -> "NORTE"
        a < 67.5 -> "NORESTE"
        a < 112.5 -> "ESTE"
        a < 157.5 -> "SURESTE"
        a < 202.5 -> "SUR"
        a < 247.5 -> "SUROESTE"
        a < 292.5 -> "OESTE"
        else -> "NOROESTE"
    }
}

data class DatosEstado(
    val fondo: Color,
    val texto: Color,
    val icono: androidx.compose.ui.graphics.vector.ImageVector,
    val mensaje: String
)

@Composable
fun PantallaPrincipal(orientationProvider: OrientationProvider) {

    var estadoActual by remember { mutableStateOf(EstadoJuego.INICIO) }
    var tiempoRestanteFalso by remember { mutableStateOf(25) }
    var temperaturaFalsa by remember { mutableStateOf("TIBIO") }
    var puntajeFalso by remember { mutableStateOf(120) }

    val azimuth by orientationProvider.azimuth
    var direccionObjetivo by remember { mutableFloatStateOf(GameLogic.generarDireccionObjetivo()) }
    val diferencia = GameLogic.calcularDiferenciaAngular(azimuth, direccionObjetivo)
    val estadoTemperaturaReal = GameLogic.obtenerEstadoTemperatura(diferencia)

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { estadoActual = EstadoJuego.INICIO }) { Text("Inicio") }
            TextButton(onClick = { estadoActual = EstadoJuego.JUGANDO }) { Text("Jugando") }
            TextButton(onClick = { estadoActual = EstadoJuego.VICTORIA }) { Text("Victoria") }
            TextButton(onClick = { estadoActual = EstadoJuego.DERROTA }) { Text("Derrota") }
        }

        when (estadoActual) {
            EstadoJuego.INICIO -> PantallaInicio(
                onIniciarClick = { estadoActual = EstadoJuego.JUGANDO }
            )

            EstadoJuego.JUGANDO -> PantallaJuego(
                tiempoRestante = tiempoRestanteFalso,
                puntaje = puntajeFalso,
                temperatura = temperaturaFalsa,
                diferencia = diferencia,
                azimuth = azimuth,
                direccionObjetivo = direccionObjetivo,
                onReiniciarClick = { estadoActual = EstadoJuego.INICIO }
            )

            EstadoJuego.VICTORIA -> PantallaVictoria(
                puntaje = puntajeFalso,
                onJugarDeNuevoClick = { estadoActual = EstadoJuego.INICIO }
            )

            EstadoJuego.DERROTA -> PantallaDerrota(
                onReintentarClick = { estadoActual = EstadoJuego.INICIO }
            )
        }
    }
}

@Composable
fun PantallaInicio(onIniciarClick: () -> Unit) {

    var mostrarInstrucciones by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(GradienteInicio1, GradienteInicio2)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.TravelExplore,
                    contentDescription = "Ícono del juego Escondidas",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Escondidas",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "¡Bienvenido, explorador!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = GrisTexto
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Gira físicamente tu teléfono para encontrar al " +
                            "personaje escondido antes de que se acabe el tiempo.",
                    fontSize = 15.sp,
                    color = GrisTexto
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            TarjetaEstadistica(
                icono = Icons.Filled.Timer,
                etiqueta = "Mejor tiempo",
                valor = "00:32",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            TarjetaEstadistica(
                icono = Icons.Filled.EmojiEvents,
                etiqueta = "Mejor puntaje",
                valor = "850 pts",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onIniciarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TibioNaranja)
        ) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Nueva partida", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { mostrarInstrucciones = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cómo jugar")
        }
    }

    if (mostrarInstrucciones) {
        AlertDialog(
            onDismissRequest = { mostrarInstrucciones = false },
            confirmButton = {
                TextButton(onClick = { mostrarInstrucciones = false }) {
                    Text("Entendido")
                }
            },
            title = { Text("Cómo jugar") },
            text = {
                Text(
                    "Gira tu teléfono en distintas direcciones. La app te " +
                            "indicará si estás Frío, Tibio o Caliente según qué tan " +
                            "cerca estés de la dirección donde se escondió el personaje."
                )
            }
        )
    }
}

@Composable
fun TarjetaEstadistica(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    etiqueta: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FrioAzulClaro)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icono, contentDescription = null, tint = GrisTexto)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valor, fontWeight = FontWeight.Bold, color = GrisTexto)
            Text(text = etiqueta, fontSize = 12.sp, color = GrisTexto)
        }
    }
}

@Composable
fun PantallaJuego(
    tiempoRestante: Int,
    puntaje: Int,
    temperatura: String,
    diferencia: Double,
    azimuth: Float,
    direccionObjetivo: Float,
    onReiniciarClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        BarraSuperiorJuego(
            tiempoRestante = tiempoRestante,
            puntaje = puntaje,
            onVolverClick = onReiniciarClick
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Gira y mueve tu teléfono para encontrar al personaje escondido.",
            fontSize = 13.sp,
            color = GrisTexto,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Brujula(azimuth = azimuth, direccionObjetivo = direccionObjetivo)
        }

        Spacer(Modifier.height(20.dp))

        BannerEstado(temperatura = temperatura)

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Está hacia el ${cardinalDe(direccionObjetivo)}",
            fontSize = 13.sp,
            color = GrisTexto,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Text("Distancia aproximada", fontSize = 12.sp, color = GrisTexto)
        Spacer(Modifier.height(6.dp))
        BarraDistancia(diferencia = diferencia)

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onReiniciarClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GrisTexto)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Reiniciar partida", fontSize = 16.sp)
        }
    }
}

@Composable
fun BarraDistancia(diferencia: Double) {
    // El progreso representa qué tan cerca está el jugador del objetivo.
    val progreso = (1 - (diferencia / 180.0)).coerceIn(0.0, 1.0).toFloat()
    val tamanoMarcador = 22.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.horizontalGradient(listOf(FrioAzul, TibioNaranja, CalienteRojo)))
    ) {
        val espacioDisponible = maxWidth - tamanoMarcador
        val desplazamiento = espacioDisponible * progreso

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = desplazamiento)
                .size(tamanoMarcador)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, GrisTexto, CircleShape)
        )
    }

    Spacer(Modifier.height(4.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Muy frío", fontSize = 11.sp, color = GrisTexto)
        Text("¡Caliente!", fontSize = 11.sp, color = GrisTexto)
    }
}

@Composable
fun ChipInfo(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    texto: String,
    colorFondo: Color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(colorFondo)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = GrisTexto, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text(texto, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GrisTexto)
    }
}

@Composable
fun BarraSuperiorJuego(tiempoRestante: Int, puntaje: Int, onVolverClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onVolverClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver al inicio", tint = GrisTexto)
        }

        ChipInfo(
            icono = Icons.Filled.Timer,
            texto = formatoTiempo(tiempoRestante),
            colorFondo = if (tiempoRestante <= 10) CalienteRojoClaro else FrioAzulClaro
        )

        ChipInfo(
            icono = Icons.Filled.Star,
            texto = "$puntaje pts",
            colorFondo = TibioNaranjaClaro
        )
    }
}

@Composable
fun Brujula(azimuth: Float, direccionObjetivo: Float) {
    // Calcula el ángulo que debe seguir la flecha desde la orientación actual.
    val anguloRelativo = ((direccionObjetivo - azimuth) + 360f) % 360f

    Box(modifier = Modifier.size(220.dp), contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color.White, radius = size.minDimension / 2)

            drawCircle(
                color = Color(0xFFE0E0E0),
                radius = size.minDimension / 2,
                style = Stroke(width = 4.dp.toPx())
            )

            val anguloRadianes = Math.toRadians((anguloRelativo - 90).toDouble())
            val largoLinea = size.minDimension / 2 * 0.75f
            val centroX = size.width / 2
            val centroY = size.height / 2
            val finX = centroX + largoLinea * Math.cos(anguloRadianes).toFloat()
            val finY = centroY + largoLinea * Math.sin(anguloRadianes).toFloat()

            drawLine(
                color = TibioNaranja,
                start = Offset(centroX, centroY),
                end = Offset(finX, finY),
                strokeWidth = 10.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        Text(
            "N", fontWeight = FontWeight.Bold, color = GrisTexto,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 6.dp)
        )
        Text(
            "S", fontWeight = FontWeight.Bold, color = GrisTexto,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
        )
        Text(
            "E", fontWeight = FontWeight.Bold, color = GrisTexto,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 6.dp)
        )
        Text(
            "O", fontWeight = FontWeight.Bold, color = GrisTexto,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 6.dp)
        )

        Icon(
            imageVector = Icons.Filled.Pets,
            contentDescription = "Personaje escondido",
            tint = GrisTexto,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(6.dp)
        )
    }
}

@Composable
fun BannerEstado(temperatura: String) {
    val datos = when (temperatura) {
        "FRIO" -> DatosEstado(FrioAzulClaro, FrioAzul, Icons.Filled.AcUnit, "MUY LEJOS")
        "TIBIO" -> DatosEstado(
            TibioNaranjaClaro,
            TibioNaranja,
            Icons.Filled.LocalFireDepartment,
            "TE ESTÁS ACERCANDO"
        )

        "CALIENTE" -> DatosEstado(
            CalienteRojoClaro,
            CalienteRojo,
            Icons.Filled.Warning,
            "¡MUY CERCA!"
        )

        else -> DatosEstado(Color.LightGray, GrisTexto, Icons.Filled.HelpOutline, "DESCONOCIDO")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(datos.fondo)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(datos.icono, contentDescription = null, tint = datos.texto)
        Spacer(Modifier.width(8.dp))
        Text(datos.mensaje, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = datos.texto)
    }
}

@Composable
fun PantallaVictoria(
    puntaje: Int,
    onJugarDeNuevoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(VerdeExitoClaro),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.EmojiEvents,
                        contentDescription = "Trofeo de victoria",
                        tint = VerdeExito,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Lo encontraste!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeExito
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    FilaResultado(
                        icono = Icons.Filled.Timer,
                        etiqueta = "Tiempo usado",
                        valor = "00:32",
                        modifier = Modifier.weight(1f)
                    )
                    FilaResultado(
                        icono = Icons.Filled.Star,
                        etiqueta = "Puntaje",
                        valor = "$puntaje pts",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onJugarDeNuevoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VerdeExito)
        ) {
            Icon(Icons.Filled.Replay, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Jugar de nuevo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FilaResultado(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    etiqueta: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icono, contentDescription = null, tint = GrisTexto)
        Spacer(Modifier.height(4.dp))
        Text(valor, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = GrisTexto)
        Text(etiqueta, fontSize = 12.sp, color = GrisTexto)
    }
}

@Composable
fun PantallaDerrota(onReintentarClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(CalienteRojoClaro),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.TimerOff,
                        contentDescription = "Tiempo agotado",
                        tint = CalienteRojo,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Se acabó el tiempo!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CalienteRojo
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "No encontraste al personaje a tiempo.",
                    fontSize = 15.sp,
                    color = GrisTexto,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReintentarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CalienteRojo)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Reintentar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}


