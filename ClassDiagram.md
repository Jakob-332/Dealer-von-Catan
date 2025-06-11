# Catan - Vereinfachte Architektur

## 1. Hauptkomponenten (vereinfacht)

```mermaid
classDiagram
direction LR

%% Hauptkomponenten
class Game {
  -currentPhase: GamePhase
  -currentPlayer: Player
  -winner: Player
  +diceRoll(): int
  +produceResources(int diceRoll)
  +checkVictory(): boolean
}

class GameController {
  -game: Game
  +nextPhase()
  +handlePlayerTurn()
  +endGame()
}

class Player {
  -id: int
  -name: String
  -victoryPoints: int
  -longestRoadLength: int
  +canBuild(BuildingType type): boolean
  +addVictoryPoints(int points)
}

class Board {
  -graph: Graph
  -robber: Robber
  +getValidBuildLocations(BuildingType): List<Location>
}

class Bank {
  -resources: Map<ResourceType, Integer>
  +hasResources(Map<ResourceType, Integer>): boolean
  +trade(Player player, ResourceType give, ResourceType get)
}

class ViewController {
  +updateGameState()
  +showPlayerTurn()
  +handleUserInput()
}

%% Beziehungen
Game --> GameController : controlled by
Game --> Player : manages
Game --> Board : uses
Game --> Bank : manages
GameController --> ViewController : updates
```

## 2. Spielmechanik (ohne Entwicklungskarten)

```mermaid
classDiagram
direction TB

%% Spielphasen
class GamePhase {
  <<enumeration>>
  +SETUP_ROUND_1
  +SETUP_ROUND_2
  +DICE_ROLL
  +RESOURCE_PRODUCTION
  +ROBBER_MOVE
  +TRADE
  +BUILD
  +END_TURN
  +GAME_OVER
}

%% Spielmechanik
class Game {
  -currentPhase: GamePhase
  -players: List<Player>
  -currentPlayerIndex: int
  -dice: Dice
  +rollDice(): int
  +moveRobber(HexTile newLocation)
  +calculateLongestRoad(Player player): int
}

class TurnManager {
  -currentPlayerIndex: int
  +nextPlayer(): Player
  +getCurrentPlayer(): Player
  +isSetupPhase(): boolean
}

class TradeController {
  +bankTrade(Player player, ResourceType give, ResourceType get, int ratio)
  +playerTrade(Player p1, Player p2, Map<ResourceType,Integer> offer)
}

class BuildController {
  +buildRoad(Player player, Edge location): boolean
  +buildSettlement(Player player, CrossingNode location): boolean
  +canBuild(Player player, BuildingType type, Location loc): boolean
}

class SetupManager {
  -round: int
  +placeInitialSettlement(Player player, CrossingNode location)
  +placeInitialRoad(Player player, Edge location)
  +isSetupComplete(): boolean
}

class VictoryManager {
  +calculateVictoryPoints(Player player): int
  +checkWinCondition(Player player): boolean
  +updateLongestRoad()
}

%% Beziehungen
Game --> GamePhase
Game --> TurnManager
Game --> SetupManager
Game --> VictoryManager
Game --> TradeController
Game --> BuildController
```

## 3. Spielbrett und Spieler (ohne Städte)

```mermaid
classDiagram
direction LR

%% Spielbrett
class Board {
  -graph: Graph
  -hexTiles: List<HexTile>
  +initializeStandardBoard()
}

class Graph {
  -crossingNodes: Map<Integer, CrossingNode>
  -edges: List<Edge>
  +findLongestRoad(Player player): int
  +getAdjacentNodes(CrossingNode node): List<CrossingNode>
}

class CrossingNode {
  -id: int
  -adjacentHexes: List<HexTile>
  -building: Settlement
  -validForBuilding: boolean
  +canPlaceSettlement(): boolean
  +getAdjacentEdges(): List<Edge>
}

class Edge {
  -id: int
  -node1: CrossingNode
  -node2: CrossingNode
  -road: Road
  +canPlaceRoad(): boolean
  +isConnectedTo(Player player): boolean
}

class HexTile {
  -id: int
  -resourceType: ResourceType
  -diceNumber: int
  -harbor: Harbor
  -hasRobber: boolean
  +produceResource(): ResourceType
}

class Harbor {
  -resourceType: ResourceType  // null für 3:1 Häfen
  -tradeRatio: int
  +getTradeRatio(ResourceType resource): int
}

class Robber {
  -currentHexTile: HexTile
  +moveToHex(HexTile newLocation)
  +blockProduction(HexTile tile): boolean
}

%% Spieler (vereinfacht)
class Player {
  -id: int
  -name: String
  -color: PlayerColor
  -victoryPoints: int
  -hand: Hand
  -settlements: List<Settlement>
  -roads: List<Road>
  -hasLongestRoad: boolean
  +addResources(Map<ResourceType, Integer> resources)
  +removeResources(Map<ResourceType, Integer> resources)
  +getTotalVictoryPoints(): int
}

class Hand {
  -resources: Map<ResourceType, Integer>
  +addResource(ResourceType type, int amount)
  +removeResource(ResourceType type, int amount)
  +getTotalCards(): int
  +hasResources(Map<ResourceType, Integer> required): boolean
}

%% Gebäude (nur Settlement und Road)
class Building {
  <<abstract>>
  -owner: Player
  -location: Location
}

class Settlement {
  -crossingNode: CrossingNode
  +getVictoryPoints(): int  // Immer 1
  +getAdjacentHexes(): List<HexTile>
}

class Road {
  -edge: Edge
  +isConnectedToPlayerBuilding(): boolean
}

%% Enumerationen
class ResourceType {
  <<enumeration>>
  +WOOD
  +BRICK
  +WOOL
  +GRAIN
  +ORE
  +DESERT
}

class PlayerColor {
  <<enumeration>>
  +RED
  +BLUE
  +WHITE
  +ORANGE
}

%% Beziehungen
Board --> Graph
Board --> HexTile
Board --> Robber
Graph --> CrossingNode
Graph --> Edge
CrossingNode --> Settlement
Edge --> Road
HexTile --> Harbor
HexTile --> ResourceType
Player --> Hand
Player --> Settlement
Player --> Road
Settlement --|> Building
Road --|> Building
```

## 4. GUI (JavaFX) - Vereinfacht

```mermaid
classDiagram
direction TB

class MainApplication {
  +start(Stage primaryStage)
  +main(String[] args)
}

class GameView {
  -boardView: BoardView
  -playerInfoView: PlayerInfoView
  -gameControlsView: GameControlsView
  +initializeLayout()
  +updateGameState(Game game)
}

class BoardView {
  -hexagonPane: Pane
  -settlements: List<Circle>
  -roads: List<Line>
  +renderBoard(Board board)
  +highlightValidLocations(List<Location> locations)
  +onHexClick(HexTile hex)
  +onNodeClick(CrossingNode node)
  +onEdgeClick(Edge edge)
}

class PlayerInfoView {
  -resourceLabels: Map<ResourceType, Label>
  -victoryPointsLabel: Label
  +updateResources(Player player)
  +updateVictoryPoints(Player player)
  +highlightCurrentPlayer(Player player)
}

class GameControlsView {
  -rollDiceButton: Button
  -endTurnButton: Button
  -tradeButton: Button
  +enableControls(List<GameAction> availableActions)
  +showGamePhase(GamePhase phase)
}

class TradeDialog {
  -bankTradePane: VBox
  -playerTradePane: VBox
  +showBankTrade(Player player)
  +showPlayerTrade(List<Player> players)
}

class GameController {
  -game: Game
  -gameView: GameView
  +handleDiceRoll()
  +handleBuildSettlement(CrossingNode location)
  +handleBuildRoad(Edge location)
  +handleTrade(TradeOffer offer)
  +handleEndTurn()
}

%% Event System
class GameEvent {
  -type: EventType
  -data: Object
  +getType(): EventType
  +getData(): Object
}

class EventType {
  <<enumeration>>
  +DICE_ROLLED
  +RESOURCES_PRODUCED
  +BUILDING_PLACED
  +TURN_ENDED
  +GAME_WON
}

%% Beziehungen
MainApplication --> GameView
GameView --> BoardView
GameView --> PlayerInfoView
GameView --> GameControlsView
GameView --> TradeDialog
GameController --> GameView
GameController --> Game
GameController --> GameEvent
```

## 5. Ressourcen und Konstanten

```mermaid
classDiagram
direction TB

class GameConstants {
  +VICTORY_POINTS_TO_WIN: int = 10
  +LONGEST_ROAD_MIN_LENGTH: int = 5
  +LONGEST_ROAD_POINTS: int = 2
  +SETTLEMENT_VICTORY_POINTS: int = 1
  +INITIAL_SETTLEMENTS_PER_PLAYER: int = 2
  +INITIAL_ROADS_PER_PLAYER: int = 2
  +BANK_TRADE_RATIO: int = 4
  +HARBOR_TRADE_RATIO_GENERIC: int = 3
  +HARBOR_TRADE_RATIO_SPECIFIC: int = 2
}

class BuildingCosts {
  +SETTLEMENT_COST: Map<ResourceType, Integer>
  +ROAD_COST: Map<ResourceType, Integer>
  +getCost(BuildingType type): Map<ResourceType, Integer>
}

class BoardConstants {
  +HEX_NUMBERS: List<Integer>
  +RESOURCE_DISTRIBUTION: Map<ResourceType, Integer>
  +HARBOR_LOCATIONS: List<Integer>
  +DESERT_HEX_ID: int
}
```
