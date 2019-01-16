# Introducción a Machine Learning Cuántico - SVM

En este proyecto se implementa uno de los algoritmos cuánticos que se han desarrollado en el ámbito de Machine Learning. La máquina de vectores de soporte, que permite clasificar imágenes de caracteres 6 y 9, en su respectiva clase. Tomando como entrada, la razón entre los pixeles de la imagen, horizontal y verticalmente.

## Instalación

Preferiblemente usar en netbeans, o crear un nuevo proyecto con la opción de fuentes existentes para ejecutar uno de los dos algoritmos principales, la fase de entrenamiento y la fase de clasificación, encontrados en el paquete src/Algorithm.

```python
import foobar

foobar.pluralize('word') # returns 'words'
foobar.pluralize('goose') # returns 'geese'
foobar.singularize('phenomena') # returns 'phenomenon'
```

## Uso
En el paquete src/Data, se encuentra la clase TestingSet que contiene los datos que se desean clasificar. El usuario puede agregar o quitar según lo desee, donde los parámetros son (ratio_horizontal, ratio_vertical, etiqueta(1 para 6, -1 para 9)).

```java
public static final SVMCharacter[] TESTING_SET = {
        new SVMCharacter(0.997, -0.077, 1),
        new SVMCharacter(0.147, 0.989, -1),
        new SVMCharacter(0.999, -0.030, 1),
        new SVMCharacter(0.987, -0.161, 1),
        new SVMCharacter(0.338, 0.941, -1),
        new SVMCharacter(0.999, 0.025, 1),
        new SVMCharacter(0.439, 0.899, -1),
        new SVMCharacter(0.173, 0.985, -1)};
}
```

## Ejemplo

Al correr el algoritmo de KernelMatrixAlgorithm, se espera encontrar la matriz de kernel asociada a los datos de entrenamiento. Debería tener un output similar a este.

(-0.01 + 0.9283i) (-0.3718 + 0.0i)                                        
(-0.3718 + 0.0i) (0.01 + 0.9283i) 

Al correr el algoritmo de QuantumSVM, debe aparecer un mensaje de éste estilo, dependiendo de los datos de entrada.

------
El caracter es un: 9 - (0.1730,0.9850)
El resultado de la clasificación es: 9
------
El caracter es un: 6 - (0.9990,0.0250)
El resultado de la clasificación es: 6
------

Para mayor información leer el artículo del proyecto, Introducción a ML Cuántico.

Este proyecto es libre para todo tipo de uso.