package cl.huertohogar.app.model

import cl.huertohogar.app.R

data class Producto(
    val codigo: String,
    val nombre: String,
    val precio: String,
    val stock: String,
    val descripcion: String,
    val imagen: Int
)

// Lista de productos compartida para Catálogo y Detalle
val listaProductos = listOf(
    Producto("FR001", "Manzanas Fuji", "$1.200 / kg", "Stock: 150 kg",
        "Manzanas crujientes y dulces del Valle del Maule, ideales para postres o meriendas.", R.drawable.manzana_fuji),
    Producto("FR002", "Naranjas Valencia", "$1.000 / kg", "Stock: 200 kg",
        "Jugosas y ricas en vitamina C, perfectas para zumos frescos y naturales.", R.drawable.naranja_valencia),
    Producto("FR003", "Plátanos Cavendish", "$800 / kg", "Stock: 250 kg",
        "Plátanos dulces y maduros, fuente de energía y potasio para tu día.", R.drawable.platano_cavendish),
    Producto("VR001", "Zanahorias Orgánicas", "$900 / kg", "Stock: 100 kg",
        "Cultivadas sin pesticidas, excelentes para ensaladas o jugos saludables.", R.drawable.zanahoria_organica),
    Producto("VR002", "Espinacas Frescas", "$700 / bolsa 500g", "Stock: 80 bolsas",
        "Ricas en hierro y fibra, perfectas para ensaladas o batidos verdes.", R.drawable.espinacas_frescas),
    Producto("VR003", "Pimientos Tricolores", "$1.500 / kg", "Stock: 120 kg",
        "Rojos, amarillos y verdes, llenos de color, antioxidantes y sabor.", R.drawable.pimientos_tricolores),
    Producto("PO001", "Miel Orgánica", "$5.000 / frasco 500g", "Stock: 50 frascos",
        "Miel pura de apicultores locales, rica en antioxidantes y sabor natural.", R.drawable.miel_organica),
    Producto("PO003", "Quinua Orgánica", "$3.500 / kg", "Stock: 60 kg",
        "Grano nutritivo, ideal como base de ensaladas o guarniciones saludables.", R.drawable.quinua_organica),
    Producto("PL001", "Leche Entera", "$1.200 / litro", "Stock: 100 litros",
        "Leche fresca de granjas locales, rica en calcio y nutrientes.", R.drawable.leche_entera)
)