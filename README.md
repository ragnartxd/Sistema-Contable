# Sistema Contable - Almacén Refrigas

**Sistema Contable** es una aplicación de escritorio desarrollada en **Java** diseñada para la gestión financiera, control de caja diaria y administración de clientes para el almacén *Refrigas*. El proyecto sigue el patrón de arquitectura **MVC (Modelo-Vista-Controlador)** para garantizar una separación limpia de responsabilidades y un código mantenible.

## 🚀 Características Principales

*   **Autenticación Segura:** Control de acceso mediante Login y Registro de Usuarios con asignación de Roles.
*   **Gestión de Caja:** Monitoreo y registro detallado de los ingresos diarios, flujos de caja e historial de movimientos.
*   **Módulo de Clientes y Deudas:** Registro de clientes junto con el control y seguimiento de cuentas por cobrar (deudas).
*   **Control de Egresos:** Registro detallado de gastos operacionales diarios.
*   **Múltiples Métodos de Pago:** Soporte para transacciones en efectivo y pagos registrados a través de Datáfono.
*   **Persistencia de Datos:** Almacenamiento local estructurado mediante archivos planos administrados de forma automática.

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Java SE (Standard Edition)
*   **Interfaz Gráfica:** Java Swing / AWT para el desarrollo de la GUI de escritorio.
*   **IDE de Desarrollo:** Soporte configurado para Eclipse e IntelliJ IDEA.
*   **Persistencia:** Manejo de archivos de texto (`.txt`) para resguardar la información del sistema de manera liviana.

## 📂 Estructura del Proyecto

El código fuente está organizado bajo los siguientes paquetes principales dentro del directorio `src/`:

*   📁 `gui/`: Contiene las interfaces visuales, ventanas (`JFrame`), paneles (`JPanel`) y renderizadores personalizados de tablas (ej. `LoginGUI`, `AlmacenRefrigasGUI`, `PanelCaja`).
*   📁 `modelo/`: Alberga las clases de negocio y entidades del sistema (ej. `Usuario`, `Cliente`, `Caja`, `Gasto`, `Deuda`, `IngresosDiarios`).
*   📁 `persistencia/`: Gestiona la lectura y escritura de datos en los archivos locales (`DataManagerText`).

---
Desarrollado para optimizar la administración y control financiero interno.
