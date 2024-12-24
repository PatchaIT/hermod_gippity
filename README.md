# hermod_gippity

[![development status | 1 - planning](https://img.shields.io/badge/development_status-1_--_planning-yellow)](https://pypi.org/classifiers/)
![license](https://img.shields.io/badge/license-MIT-green)
![release](https://img.shields.io/github/v/release/PatchaIT/hermod_gippity)
[![next](https://img.shields.io/badge/next-v0.0.1-yellow)](https://github.com/PatchaIT/hermod_gippity/tree/hermod_gippity_v0.0.1)

Send message to JMS queue

## Table of Contents

* [hermod\_gippity](#hermod_gippity)
  * [Table of Contents](#table-of-contents)
  * [About The Project](#about-the-project)
    * [It is what it is](#it-is-what-it-is)
    * [It is an experiment](#it-is-an-experiment)
  * [In Shorts](#in-shorts)
  * [Changelog](#changelog)
  * [Implementing in v0.0.1](#implementing-in-v001)

## About The Project

This project is two things.

### It is what it is

First it is what it is: an application which will let you send a message
    to a JMS queue.  
  It will start as a very basical command line project, needing a JVM
    installation, and then will became more and more with time, while evolving.

### It is an experiment

Second: this is an experiment, too.  
  I'll make this project in collaboration with a virtual coworker.
  Yes, I'm talking about AI.
  But not like I'll just tell AI: `I want an app which does this. Please
    do it for me now!`, but I'll face the AI almost like a real coworker.
  Well a cowoker whom doesn't drink coffee, but that's ok.  
  I'll refer her as female, named Chantal.

We already decided something "together". Which means I proposed some
  alternative options to choose from, and she helped me to make the
  best choice.

For instance: `I want to do X and I have Y necesseities, better I adopt
  Java 17 or Java 21? Eclipse or IntelliJ?`

Even if some other things were decided just by me, like the adoption of Java
  language with Maven tools.

Even the name was chosen by me proposing stuffs, and obviously staring
  from an idea of mine.  
I wanted a classical approach, which means adopting a messenger deity, and
  I also wanted to add an indirect reference to the adopted AI.  
But I also want an original deity name which was also easy to remember, and
  so first I asked her a series of possible deity, and then I asked her which
  one was better for the project name by originality and readability.
So it was pratically her deciding the deity based on my requisites.

If you're smart enough, you'll guess which AI I chose, but the second part
  of the name.
I know there are lot of new AI specialized to the needs of a developer, but
  on purpose I wanted instaed to try the experiment with a more generical
  purpose AI.

Chat highlights with the AI:
 [AI Chat Highlights for hermod_gippity Project](https://gist.github.com/PatchaIT/018625b7f192eb3494882ad85ae7d6fd#file-aihighlights_hermodgippity-md)

## In Shorts

* Project: Hermod Gippity
* Version: 0.0.1-SNAPSHOT
* Version: 0.0.1 - Skeleton
* Description: Will send message to JMS queue, but at the moment does nothing.
* Overlays: None
* Made By: @PatchaIT
* Update Date: 2024/12/22

* [SonarCloud Project](https://sonarcloud.io/summary/overall?id=PatchaIT_hermod_gippity&branch=main)

## Changelog

* 2025/01/xx v0.0.1
  * First release

## Implementing in v0.0.1

Version v0.0.1 is a work in progress in the branch
  [hermod_gippity_v0.0.1](https://github.com/PatchaIT/hermod_gippity/tree/hermod_gippity_v0.0.1).

Up to now the following is implemented:

* Defined Folder Structure
* Stub main class (up to now it only tells you if you put an argument or not)
* Stub jUnit test (actually tests the "nothing" the main does)
* Implemented maven-shade-plugin to generate a fat jar
* Implemented maven-assembly-plugin to generate a custom named executable jar
* Implemented SonarCloud to check code quality:
  [SonarCloud Project](https://sonarcloud.io/summary/overall?id=PatchaIT_hermod_gippity&branch=main)
* Implemented a logger (but still writes on console)
* Adopted the most recent stable versions of all dependencies
