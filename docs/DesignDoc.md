---
geometry: margin=1in
---
# WebCheckers Design Documentation

# Team Information
* Team name: Group "B emoji"
* Team members
  * Andrew Reed
  * Curtis Veronesi
  * Peter Fabinski
  * Adam Heeter

# Executive Summary

> WebChecker is a platform designed for players to link up and play checkers online. Players get to choose to either to play against an Artificial Intelligence or to choose from a list of active players that are not in a game. After choosing the type of game to play, the user is brought to a screen with the board shown on it, and their opponent also is brought to the same game but from the opposite perspective. From there, they will continue to play the game while being held to the rules of the game. Lastly, players will be able to save and re-watch games they have played in the past.

## Purpose

> Web checkers is a platform for users to come and play checkers online with either their friends or an artificial intelligence.
>
> Our user group includes:
>
> > Parents and childern, who want to add more fun to game night. 
> >
> > Groups of Friends, who are looking for something fun to play.

## Glossary and Acronyms
> Provide a table of terms and acronyms.

| Term | Definition |
|------|------------|
| VO | Value Object |

# Requirements

This section describes the features of the application.

> In this section you do not need to be exhaustive and list every story.  Focus on top-level features from the Vision document and maybe Epics and critical Stories.

## Definition of MVP

> Provide a simple description of the Minimum Viable Product.

## MVP Features

> Player Sign-in

> > As a Player I want to sign-in so that I can play a game of checkers.

> Start a Game

> > As a Player I want to start a game so that I can play checkers with an opponent.

> Start a Game

> > As a Player I want to start a game so that I can play checkers with an opponent.

> Epic: Piece Movement

> > As a Player I want to play a game of checkers so that I have fun.

> > Allowed Spaces

> > > As a Player I want to get feedback when I drag a piece so that I know where I am allowed to put it down.

> > Piece Capture

> > > As a Player I want to capture the other player's pieces so that I can progress in the game.

> > Forced Moves

> > > As a Player I want to be informed when I am required to make a move so that I don’t try to make a different move than I need to.

> Epic: Game State

> > The player needs to know the state of the game and what they can do next.

> > Game Endings 

> > > As a Player I want to know when the game is over so that I don't try to continue playing.


## Roadmap of Enhancements

> Epic: Save Management

> > The player must be able to choose when they want to save a game and look at them later.

> > Listing Saved Games

> > > As a Player I want to view the games which I have saved so that I can choose which one to watch.

> > Saving a Game

> > > As a Player I want to decide, at the end of a game, to save it so that I can review it at a later date.

> > Deleting a Game

> > > As a Player I want to delete a game I have saved so that it does not clutter up my list of saved games.

> Epic: Artificial Intelligence

> > As a Player I want to play against a computer player so that I can play checkers without needing another player to play against.

> > Movement

> > > As an AI I want to move checkers so that the game can be played.

> > Location Take Over

> > > As an AI I want to take positions so that I can win.

> > AI Learning

> > > As an AI I want to Learn from mistakes so that I can win every game.

> > Easy AI Extra

> > > As an easy AI I want to make jokes so that the player is entertained.

> Player Selection 

> > As a Player I want to choose an AI so that I can play a game of checkers against it.

> Epic: Watching a Saved Game

> > The player must be able to pick a saved game and watch the playthrough.

> > Viewing a Game

> > > As a Player I want to see the replayed game in a similar setting to where it was played originally so that I can easily track the movements and game status.

> > Play and Pause

> > > As a Player I want to pause and resume watching a replay game so that I can stop to analyze a position or practice my own playing.

> > Speed Controls

> > > As a Player I want to control the speed of playback of a game so that I can slow it down and look carefully or go quickly through and get the basic idea of the game.

> > Player Information

> > > As a Player watching a game, I want to be told the names of each player in the game so that I know who I am watching.


# Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model.png)

Web Checkers is based primarily upon individual games of checkers, each having a board and two players. The board contains 64 spaces of alternating colors; each player's pieces can be placed on the dark squares of the board. Each player has 12 pieces; one player has red pieces, and one player has white pieces. When moving pieces, the players are restricted by the rules of Checkers, which dictate where they are allowed to move and what actions they can take. When a game is complete, either player can decide to save the game to their savegame list to rewatch it at a later date.


# Architecture

This section describes the application architecture.

## Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a browser.  The client-side
of the UI is composed of HTML pages with some minimal CSS for styling the page.  There is also
some JavaScript that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below


## Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface.png)

When the user first logs in, they are directed to the home page, which will show them the total number of online players, as well as a link to the sign in page. When they click the link to sign in, they see a text box where they can enter a username. If the username is taken or invalid, they will see a message and be directed back to the sign in page. If they enter a valid, unused username, they will be sent back to the home page with a link to sign out and a full list of players' usernames. When they click on another user, if they are not in a game already, the two players will be placed in a game together and sent to the Game page. Until the game is over, it alternates between being the red and white player's turn.


## UI Tier
> Provide a summary of the Server-side UI tier of your architecture.

> Describe the types of components in the tier and describe their responsibilities.

### Static models
> Provide one or more static models (UML class or object diagrams) with some details such as critical attributes and methods.

### Dynamic models
> Provide any dynamic models, such as state and sequence diagrams, as is relevant to a particularly significant user story.

> For example, in WebCheckers you might create a sequence diagram of the `POST /validateMove` HTTP request processing or you might use a state diagram if the Game component uses a state machine to manage the game.


### Application Tier
> Provide a summary of the Application tier of your architecture.

> Describe the types of components in the tier and describe their responsibilities.

### Static models
> Provide one or more static models (UML class or object diagrams) with some details such as critical attributes and methods.

### Dynamic models
> Provide any dynamic model, such as state and sequence diagrams, as is relevant to a particularly significant user story.


## Model Tier
> Provide a summary of the Model tier of your architecture.

> Describe the types of components in the tier and describe their responsibilities.

### Static models
> Provide one or more static models (UML class or object diagrams) with some details such as critical attributes and methods.

### Dynamic models
> Provide any dynamic model, such as state and sequence diagrams, as is relevant to a particularly significant user story.
