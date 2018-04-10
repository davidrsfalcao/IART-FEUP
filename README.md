# IART-FEUP [![CircleCI](https://circleci.com/gh/davidrsfalcao/IART-FEUP.svg?style=svg&circle-token=e013c47d3aca450c8b4972421c7aa46794ad3fea)](https://circleci.com/gh/davidrsfalcao/IART-FEUP) [![Build Status](https://travis-ci.com/davidrsfalcao/IART-FEUP.svg?token=C6xRbcS3ihWjtStee4Yn&branch=master)](https://travis-ci.com/davidrsfalcao/IART-FEUP)


Trabalho prático de Inteligência Artificial, FEUP


## A2: Pesquisa aplicada à evacuação

### Index
1. [Objetivo](#objetivo)
2. [Descrição](#descrição)
3. [Entrega Intercalar](#entrega-intercalar)
4. [Developers](#developers)

### Objetivo
```
Determinar um plano de evacuação de um conjunto de turistas retidos numa montanha.
```

### Descrição

```
Pretende-se evacuar turistas que ficaram retidos numa montanha. Estão disponíveis veículos de 
transporte localizados em n pontos/locais estratégicos. 
É também conhecido o local do abrigo para onde devem ser evacuados os turistas. 
Os veículos possuem capacidade limitada e só existe um veículo em cada um dos n pontos estratégicos 
considerados.

O programa deve determinar o percurso ótimo para evacuar todos os turistas no menor tempo. O veículo 
de transporte pode não possuir capacidade suficiente para transportar todos os turistas que se 
encontram num determinado local, pelo que deve: o mesmo veículo efetuar mais que uma viagem; ou usar 
um segundo veículo.

Considere duas versões deste trabalho:

> versão 1: Os turistas a evacuar estão no mesmo local.

> versão 2: Os turistas a evacuar estão em locais distintos.
```

### Entrega Intercalar
![](https://i.gyazo.com/0633eeeb2594d6b3bf4c33d467fef257.png)***Figura 1:*** Exemplo de Grafo Representativo da situação descrita

```

Inicialmente o grupo efetuou pesquisa sobre a melhor forma de mostrar o grafo representativo dos
locais. Optamos por utilizar a framework JUNG (Java Universal Network/Graph Framework) [1]. 
O código necessário para o grafo foi implementado utilizando a linguagem de programação JAVA. 
Criamos classes que representam componentes do grafo, assim como elementos que representam pessoas
e veículos. Para a criação do grafo é efetuada a leitura de um ficheiro XML, o que permite adicionar 
locais mais facilmente consoante o desenvolvimento. Os locais não têm coordenadas fixas, apenas as 
ligações entre os mesmos são representadas pela distância, e nome da ligação. Do mesmo modo, é 
efetuada uma leitura para obter os grupos de turistas que se encontram em cada local. De forma a 
guardar estas informações, foram criadas estruturas de dados, nomeadamente listas.
```

### Developers

```
Cláudia Rodrigues
David Falcão
Pedro Miranda
```
