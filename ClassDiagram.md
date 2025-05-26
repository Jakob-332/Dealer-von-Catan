---
config:
  theme: default
  layout: elk
---
classDiagram
direction TB

class ResourceType {
  <<enumeration>>
  +WOOD
  +BRICK
  +SHEEP
  +WHEAT
  +ORE
}

class GamePhase {
  <<enumeration>>
  +SETUP
  +DICE_ROLL
  +RESOURCE_PRODUCTION
  +TRADE
  +PLAY_DEV_CARD
  +BUILD
  +END_TURN
  +END_GAME
}

class Trade {
  <<abstract>>
  -offeringPlayer: Player
  -requestedResource: ResourceType
  -offeringResource: ResourceType
  +checkIfPossible()
}

class DevelopmentCard {
  <<abstract>>
  -infoText: String
  +cardAction()
}

class Building {
  <<abstract>>
  -posX: int
  -posY: int
  -owner: Player
}

class Game {
  +relocateRobber()
  +diceThrow()
  +currentPhase: GamePhase
  +calculateLongestRoad(Player player)
  +checkForKnightEmpire(Player player)
}

class GameController {
  +nextPhase()
  +handleTurn()
}

class TurnManager {
  +nextPlayer()
  +getCurrentPlayer()
}

class TradeController {
  +executeTrade()
}

class BuildController {
  +buildRoad()
  +buildSettlement()
  +upgradeCity()
}

class DevCardController {
  +playCard()
}

class SetupManager {
  +initializePlayers()
  +placeInitialSettlements()
}

class VictoryManager {
  +checkVictory(Player player)
  +declareWinner()
}

class Board {
  +createBoard()
}

class Player {
  -knightCount: int
  -victoryPoints: int
  +addResource()
  +removeResource()
  +increaseVictoryPoints()
  +getResourceInfo() Map<ResourceType, Int>
  +hasREsources(Map<ResourceTypw, Integer>) : Bool
  +buildVillage(Node location)
  +buildCity(Node location)
  +buildStreet(Edge location)
  +playDevCard(DevelopmentCard card)
}

class Hand {
  -resources
  -cards
}

class Dice {
  -diceEyes: int
  +rollDice()
}

class Robber {
  -currentTile
  +blockResourceProduction()
  +triggerSteal()
  +moveTo(HexTile tile)
  +stealFrom(Player victim)
}

class Harbor {
  -acceptedResource: ResourceType
  -tradeRate: int
}

class Knight
class VictoryPointCard
class MonopolyCard
class InventionCard
class RoadBuildingCard

Knight --|> DevelopmentCard
VictoryPointCard --|> DevelopmentCard
MonopolyCard --|> DevelopmentCard
InventionCard --|> DevelopmentCard
RoadBuildingCard --|> DevelopmentCard

class PlayerTrade
class SeaTrade

PlayerTrade --|> Trade
SeaTrade --|> Trade
SeaTrade --> Harbor

class HexTile {
  -number: int
  -resourceType: ResourceType
  -centerX: float
  -centerY: float
}

class Water
class Land

Water --|> HexTile
Land --|> HexTile

class Node {
  -posX: int
  -posY: int
}

class Edge {
  -start: Node
  -end: Node
}

class Village
class City
class Road

Village --|> Building
City --|> Building
Road --|> Building

class MainView {
  +start()
}

class PlayerView {
  +updatePlayerResources()
  +updateCards()
}

class BoardView {
  +renderBoard()
  +highlightBuildOptions()
}

class GameView {
  +updateGamePhase()
  +showWinner()
}

class ViewController {
  +connectModelToView()
  +registerListeners()
}

Game --> GameController
Game --> TurnManager
Game --> Player
Game --> Board
Game --> SetupManager
Game --> VictoryManager
Player --> Hand
Player --> Dice
Player --> DevelopmentCard
Player --> Building
Board --> HexTile
Board --> Node
Board --> Edge
HexTile --> Robber
Robber --> Player

MainView --> GameView
GameView --> BoardView
GameView --> PlayerView
ViewController --> GameController
ViewController --> GameView

class GameEvent {
  +eventType: String
  +data: Object
}

class EventPublisher {
  +subscribe(observer: Observer)
  +notify(event: GameEvent)
}

class Observer {
  +update(event: GameEvent)
}

GameController --> EventPublisher
ViewController --> Observer
Player --> EventPublisher
