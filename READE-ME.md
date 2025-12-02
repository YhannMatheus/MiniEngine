# 🎮 MiniEngine 2D

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-23.0.1-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

Uma Game Engine 2D leve e modular construída "do zero" em Java, utilizando JavaFX para renderização acelerada por hardware. Desenvolvida como projeto para a disciplina de **Programação 2**, com foco em **Orientação a Objetos**, **Padrões de Projeto** e arquitetura **Entity-Component System (ECS)**.

---

## ✨ Funcionalidades

* **Arquitetura Híbrida ECS:** Objetos (`GameObject`) compostos por componentes modulares (`GameComponent`).
* **Game Loop Robusto:** Ciclo fixo de atualização (`Update`) e renderização (`Draw`) a 60 FPS utilizando `AnimationTimer`.
* **Sistema de Cenas (Worlds):** Gerenciamento de fases e transições de tela com carregamento/descarregamento automático de objetos.
* **Renderização Inteligente:**
    * Suporte a Sprites (`SpriteRenderer`) com sistema de *fallback* visual (quadrado rosa) para debug.
    * Escala Global (Zoom) para suporte a jogos Pixel Art sem perda de qualidade.
* **Sistema de Input Agnóstico:** Detecção de Teclado e Mouse (Click, Hold e Release) sem impor controles pré-definidos.
* **Física Básica:** Sistema de colisão AABB (`BoxCollider`) com resolução automática de sobreposição (`isColliding`).
* **Matemática Vetorial:** Classe `Vector2` própria para manipulação de posição, escala e direção.
* **Gerenciamento de Recursos:** Carregamento automático de assets a partir da pasta padrão `/images`.

---

## 🛠️ Arquitetura

O projeto é dividido em dois pacotes principais para simular uma biblioteca real:

1.  **`miniengine` (Core):** Contém todo o código fonte da ferramenta. O usuário final não precisa alterar nada aqui.
    * `Game`: Singleton que gerencia a janela e o estado global.
    * `GameWindow`: O motor gráfico (JavaFX) encapsulado.
    * `GameObject` & `GameComponent`: A base do sistema de entidades.
2.  **`game` (User Land):** Onde o jogo é criado. Aqui ficam os scripts, fases e configurações do usuário.

### Ciclo de Vida do Objeto
Todo objeto no jogo segue este fluxo:
1.  **`awake()`**: Chamado na instância (`new`). Configurações iniciais.
2.  **`initialize()`**: Chamado ao entrar na cena. Conexões com outros objetos.
3.  **`update()`**: Chamado a cada frame (Lógica).
4.  **`draw()`**: Chamado a cada frame (Visual).
5.  **`dispose()`**: Chamado ao ser destruído. Limpeza de memória.

---

## 🚀 Como Usar

### Pré-requisitos
* JDK 21 ou superior (Testado no JDK 25).
* Maven.
* IDE recomendada: IntelliJ IDEA ou VS Code.

### Exemplo de Código

Criar um jogo com a **MiniEngine** é simples. Veja como criar um Player que anda e tem colisão:

**1. Criando o Objeto Player**
```java
public class MyPlayer extends GameObject {
    @Override
    public void awake() {
        // Adiciona Transform (Posição 100, 100)
        addComponent(new Transform(100, 100));
        
        // Adiciona Visual (Procura automaticamente em resources/images/player.png)
        addComponent(new SpriteRenderer("player.png", 50, 50));
        
        // Adiciona Colisor
        addComponent(new BoxCollider(50, 50));
        
        // Adiciona Script de Controle Customizado
        addComponent(new PlayerController());
    }
}