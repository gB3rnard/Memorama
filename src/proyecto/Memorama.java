package proyecto;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;


/**Inicialmente creo una clase que hereda de JFrame para que implemente las propiedades, 
 * metodos y atributos de esta clase de interfeces gráficas
 * @author Glenda
 *
 */
public class Memorama extends JFrame implements ActionListener {
	
	/**Utilizo 8 botones que contendrán las imagenes a adivinar
	 * tambien utilizo 4 imagenes que contendran los pares del juego a adivinar*/
    private JButton boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, ultimoPres;
    private ImageIcon imagen1, imagen2, imagen3, imagen4;
    
    /**Una de las variables mas importantes es la variable que va a contar los pares creados 
      para controlar cuanto va quedando para finalizar el juego*/
    private int contadorParejas;
    
 
    /**Creo un metodo para ocultar Imagenes y para mostrar Imagenes. Cada vez que un boton es presionado
     * el evento va a retornar una imagen asociada a ese boton, y luego la logica del juego 
     * indica que ese imagen debe ocultarse nuevamente
     */
    public void ocultarImagen(JButton boton) {
        boton.setIcon(null);//se utiliza el metodo setIcon para contener a una imagen dentro de un boton
        //para ocultarla se establecera a null y para mostrarla le pasaremos un parametro de tipo Imagen
    }

    public void mostrarImagen(JButton boton, ImageIcon imagen) {
        boton.setIcon(imagen);
    }
    
    
    /**El constructor principal de la clase va a contener varios elementos 
     * de la interfaz principal, como la ubicacion relativa de las imagenes y 
     * la inicializacion de los botones*/
    
    public Memorama() {
        setTitle("Juego de Memorama");
        
        /**En este caso he preferido establecer el Layout de forma absoluta
         * para poder configurar y posicionar los botones de una forma mas libre,
         * a diferencia del diseño de cuadricula
         */
        getContentPane().setLayout(null);
        
        //Inicializamos las imagenes con su ruta relativa
        imagen1 = new ImageIcon("figs_memo1.jpg");
        imagen2 = new ImageIcon("figs_memo2.jpg");
        imagen3 = new ImageIcon("figs_memo3.jpg");
        imagen4 = new ImageIcon("figs_memo4.jpg");

        
        //Inicializamos los botones, con un tamaño, añadimos la escucha de 
        //un evento y los añadimos al Layout
        boton1 = new JButton();
        boton1.setBounds(25, 31, 152, 173);
        boton1.addActionListener(this);
        getContentPane().add(boton1);
        
        boton2 = new JButton();
        boton2.setBounds(229, 31, 152, 173);
        boton2.addActionListener(this);
        getContentPane().add(boton2);
        
        
        boton3 = new JButton();
        boton3.setBounds(418, 31, 152, 173);
        boton3.addActionListener(this);
        getContentPane().add(boton3);
        
        
        boton4 = new JButton();
        boton4.setBounds(627, 31, 152, 173);
        boton4.addActionListener(this);
        getContentPane().add(boton4);
        
        boton5 = new JButton();
        boton5.setBounds(25, 235, 152, 173);
        boton5.addActionListener(this);
        getContentPane().add(boton5);
        
        
        boton6 = new JButton();
        boton6.setBounds(229, 235, 152, 173);
        boton6.addActionListener(this);
        getContentPane().add(boton6);
        
        
        boton7 = new JButton();
        boton7.setBounds(418, 235, 152, 173);
        boton7.addActionListener(this);
        getContentPane().add(boton7);
        
        
        boton8 = new JButton();
        boton8.setBounds(627, 235, 152, 173);
        boton8.addActionListener(this);
        getContentPane().add(boton8);
        
        
        //El boton Reiniciar hace una llamada al metodo reiniciarJuego
        
        JButton btnReiniciar = new JButton("Reiniciar Juego");
        btnReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        btnReiniciar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnReiniciar.setBounds(259, 466, 259, 50);
        getContentPane().add(btnReiniciar);

        setSize(852, 593);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    
    //El metodo iniciar Juego es solo llamado dentro del metodo Reiniciar
    //y basicamente establece el contador a cero y el ultimo boton presionado a null;
    public void iniciarJuego() {
    	contadorParejas = 0;
    	ultimoPres = null;
    }
    
    
    /**La accion de cada boton realiza una accion comun que es mostrarImagen, ocultar la imagen mostrada
     * e ir contado las parejas que son encontradas o no*/ 
    public void actionPerformed(ActionEvent e) {
    	/**En primer lugar nos creamos un boton como variable local que recibira el metodo getSource 
    	 * */
    	JButton boton = (JButton) e.getSource();
    	
    	/**Si el primer boton es null significa que no ha sido presionado ningun boton anterior a este
    	 * por lo que se usa el boton 'primerBoton' para almacenar la informacion del boton que ha sido presionado 
    	 * actualmente. D es esta manera la accion o evento se ejecutara sobre el ultimo boton presionado*/
        if (ultimoPres == null) {
        	ultimoPres = boton;
        	
        	/**Luego hacemos una llamada al metodo mostrar Imagen ya que un boton ha sido presionado y le pasamos 
        	 * como parametros el utlimo boton presionado y como segundo parametro el metodo obtenerImagenes que 
        	 * en dependencia del boton que haya sido presionado retornará la imagen asociada a dicho boton*/
            mostrarImagen(ultimoPres, obtenerImagenBoton(ultimoPres));
            //una vez retorna la imagen y la muestra se mantiene el boton activo para volver a ser pulsado
            ultimoPres.setEnabled(true);
            
            /**en caso contrario, si ya se ha presionado más de un boton hacemos la misma llamada al metodo 
             * de mostrar imagen*/
        } else {
            mostrarImagen(boton, obtenerImagenBoton(boton));
            
            /**Utilizo un objeto de tipo Timer(como un temporizador) para controlar 
             * el tiempo que la imagen es mostrada y lo inicializo. Dnetro de este evento 
             * realizo la comparacion de imagenes creando nuevamente un metodo de tipo ActionEvent*/
            Timer tiempodeMuestra = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	/**Aqui evaluo dos condiciones: la primera es si las imagenes mostradas de los 
                	botonoes clicados son iguales*/
                    if (obtenerImagenBoton(ultimoPres) == obtenerImagenBoton(boton)) {
                    	//si son iguales deshabilito el ultimo boton presionado y el anterior
                        boton.setEnabled(false);
                        ultimoPres.setEnabled(false);
                        //incremento el contador de parejas ya que son iguales las imagenes
                        contadorParejas++;
                        /**dado el caso que el contador de parejas fuese igual a 4(ya que son 4 pares) muestro un mensaje 
                        indicando que el juego ha finalizado correctamente*/
                        if (contadorParejas == 4) {
                            reiniciarJuego();//llamo al metodo reiniciarJuego para establecer los botones a null y el contador de parejas a cero
                            JOptionPane.showMessageDialog(Memorama.this, "¡Muy Bien, has ganado!");
                        }
                    } else {//si no son iguales las imagenes despues de presionar los botones es conveniente que estas se vuelvan a ocultar
                        ocultarImagen(boton);
                        ocultarImagen(ultimoPres);
                        boton.setEnabled(true);
                        ultimoPres.setEnabled(true);
                    }
                    ultimoPres = null;/**establezco el ultimo boton a null, ya que los eventos de presionado de boton solo 'escuchan' 
                    de dos en dos*/
                }
            });
            tiempodeMuestra.setRepeats(false);//el objeto tiempo de muestra tambien se reinicia
            tiempodeMuestra.start();
        }
    }
 
    /**Como las imagenes la he asociado una imagen por cada dos botones si cualquiera de esos dos botones es presionado 
     * lo que hace es retornar o mostrar la imagen en cuestion, si no existe la imagen o el boton, retorna null */
    private ImageIcon obtenerImagenBoton(JButton boton) {
        if (boton == boton1 || boton == boton8) {
            return imagen1;
        } else if (boton == boton2 || boton == boton5) {
            return imagen2;
        } else if (boton == boton3 || boton == boton7) {
            return imagen3;
        } else if (boton == boton4 || boton == boton6) {
            return imagen4;
        }
        return null;
    }

    /**El metodo reiniciar hace una llamado del metodo ocultar imagen evaluado en cada 
     * boton, para establecer los botones sin imagen 
    al comienzo del juego*/
    public void reiniciarJuego() {
        ocultarImagen(boton1);
        ocultarImagen(boton2);
        ocultarImagen(boton3);
        ocultarImagen(boton4);
        ocultarImagen(boton5);
        ocultarImagen(boton6);
        ocultarImagen(boton7);
        ocultarImagen(boton8);
        
        /**tambien se establecen los botones para que esten disponibles a presionar una vez que ha 
        comenzado el juego*/
        boton1.setEnabled(true);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
        boton4.setEnabled(true);
        boton5.setEnabled(true);
        boton6.setEnabled(true);
        boton7.setEnabled(true);
        boton8.setEnabled(true);
        
        //llamada al metodo iniciar que reinicia la variable contador y el primer boton presionado
        iniciarJuego();
    }

    
    //menu principal creado por defecto
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Memorama frame = new Memorama();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
