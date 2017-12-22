# A textual-based technique for smell detection
This is an implementation of [A textual-based technique for smell detection](http://ieeexplore.ieee.org/document/7503704/) by 
Fabio Palomba, Annibale Panichella, Andrea De Lucia, Rocco Oliveto, Andy Zaidman. 
The paper was published on IEEE International Conference on Program Comprehension in 2016.

## 1 Proposed textual based approach
Creating maintainable software is very important as software industry has to support software after delivery. Code smells are stumbling blocks to creating maintainable software. Code smells are usually an indication to deeper problem in the system. Most code smells can be identified easily by human. But, automated code smell detection is not very accurate yet.

Developers are more concerned about just delivering work rather than upholding standard coding style. So, code smells are introduced in code. Detecting and refactoring code smells are very important to make more maintainable software. Most of the researchers has referred code smell to be a structural characteristic of software. But we have failed to outline a border for most types of code smells. So, we have to try to find a different but more accurate smell detection technique. 

There has been many work in the field of code smell detection. Most of the researchers have considered structural characteristics to find code smells. Some suggested ad-hoc manual inspection rules, some also proposed tools to visualize and refactor smells. Code smells can be formulated as optimization problem where search algorithms and genetic algorithms are used. Most previous work relies on metrics like size, complexity etc. Different smells requires different types of metrics. In this work, textual based technique to detect different types of code smells will be used.

### 1.1 Motivation
	Improving Code Quality: In software engineering, bugs and change of requirements are eminent. If the code quality is not up to the mark, it takes a lot of time and effort to make changes. Code smell is a big reason for hindrance of code quality. 

	Minimization of Code Smells:  Code smells cannot be eradicated complete as it is a recurring problem. But, understanding the relation of textual information may help us to find more important code smells. 

	Language Independent Way of Detecting Code Smell: There are many code smell detection tools available. Most of those use metric based approach and those approaches are language dependent. Using textual information for code smell detection may lead to language independent code smell detection tool.

### 1.2 Architecture of the tool
There are three main steps for detecting code smells. They are: i) Textual Content Extractor, ii) IR Normalization Process and iii) Smell Detector

I.	Textual Content Extractor: The first step is to collect all textual information needed for the analysis. In this step, from the source code files, necessary textual information are extracted from every component.

II.	IR Normalization Process: using a typical Information Retrieval (IR) normalization process, identifiers and comments of each component are normalized. The following steps are performed: 
a.	Separating composed identifiers using the camel case splitting which splits words based on underscores, capital letters and numerical digits
b.	Reducing to lower case letters of extracted words
c.	Removing special characters, programming keywords and common English stop words
d.	Stemming words to their original roots via Porter’s stemmer Using the term frequency - inverse document frequency (tfidf) schema, the normalized words are weighted to reduce the relevance of too generic words that are contained in most source components.

III.	Smell Detector: The normalized textual content of each code component is then individually analyzed by the Smell Detector. The smell detector uses Latent Semantic Indexing (LSI)  which models code components as vectors of terms occurring in a given software system. LSI uses Singular Value Decomposition (SVD) to cluster code components according to the relationships among words and components. Here each code component is given a probability number and according to that probability, it is decided that if the code component is affected with code smell or not. 

### 1.3 Code Smell Rules
The rules used to detect code smells in five types of code smells are given below [13]:

	Long Method: This smell arises when a method does when a method is responsible for some auxiliary functionality along with its main functionality. The name of the smell does not fully express the smell type. It is more about functionality than size. So, a method can be relatively larger and even then not considered to be a long method. In the proposed tool, consecutive statements are taken into account. Let, S = {s1, s2, … ,sn} be the statements of a method M.

MethodCohesion (M) = mean i!=j sim(si, sj )

This is the value of the method’s cohesion. The value lies 0 to 1. The lesser the cohesion is, the more chance is there that the method is in fact a long method.

So, the probability of a method being a long method is,

PLM(M) = 1 − MethodCohesion(M)

This value also ranges from 0 to 1. The bigger the value is, there is a greater chance of the method to be a long method. 

	Feature Envy: When a method is more interested in other class rather than its own, it is considered feature envy [4]. So, if a method M is in Class Co and C = {c1, c2, … ,cn} is the set of classes which shares at least one term with M then, we try to find the most closest class for M using following formula:

Cclosest = arg max Ci∈Crelated sim(M, Ci)

If Cclosest is not equal to Co, then M shows feature envy.

	Blob: Blob are the classes with low cohesion among its methods. Sometimes it is also labeled as god classes. If C is the class under analysis and M = {m1,m2, … ,mn } is the set of methods of C, then the following rule can be applied to find if C is a blob.

ClassCohesion(C) = mean i!=j sim(mi , mj )

This also ranges from 0 to 1. The less the value is, the more chance is there for C to be a blob.

So, the probability of a class being a blob is

PB(C) = 1 − ClassCohesion(C)

This value lies in 0 to 1. Bigger value indicates a higher chance of a class to be a blob.

	Promiscuous Package: If a package contains classes implementing too many features, making it too hard to understand and maintain, it can be considered as promiscuous. Let C = {c1, . . . , cn} be the set of classes in P, the textual cohesion of the package P is:

PackageCohesion(P) = mean i!=j sim(ci , cj )

The value ranges from 0 to 1 and bigger probability value is better.

So, the probability of a package to be a promiscuous package is,

PPP (P) = 1 − PackageCohesion(P)

This value lies in between 0 and 1. Greater value suggest a package to be a promiscuous package.

	Misplaced Class: If a class is not related to the package it is in, then it is called misplaced class. So, if a class C is in Package Po and P = {p1, p2, … ,pn} is the set of packages which shares at least one term with C then, we try to find the most closest package for C using following formula:

Pclosest = arg max Pi∈Prelated sim(C, Pi)

If Pclosest is not equal to Po, then C shows feature envy.


## 2 The Implementation
The following figure suggests the flow of the built tool:
![alt Methodology of the tool](https://image.ibb.co/ma80LR/Palomba.png)

Figure: Proposed Code Smell Detection Process

## 3 Data
Any java project as a zip file can be used as input for this tool.

## 4 How to Use
Prerequisites : Git, Java(TM) SE Runtime Environment (JVM version: at least 1.8), Ecplise Oxygen or Spring Tool Suite (STS), Google Chrome Version 63.0.3239.84

### 4.1 Create as a Java Project
#### 4.1.1 Clone the repository
 
  ````
  git clone https://github.com/Sabir001/smell-detection.git  
  ````
#### 4.1.2 Import project
Import project in eclipse as a maven project. Required jars will not be available if you choose other type of project while importing
#### 4.1.3 Run Application
Go to WebAppForSmellDetection.java file. Right click and then click on "Run as Java Application" or "Run as Spring Boot Application".
Then go to google chrome and hit the following url: 
http://localhost:8080/ 

Then click on "Upload project" button. Then choose file. Only zip file of less than 10 megabytes can be uploaded. Then hit the "Submit" button.

Code smells of five types will be shown after processing in browser. Those smells types are Blob, Long Method, Feature Envy, Misplaced Class and Promiscuous Package.

## 5 Threats to validity
This section describes the threats which can affect the validity of this study. 
### 5.1 Construction Validity
Four student projects and two normal projects have been considered for this study. All of those four student projects are from BSSE 6th Batch of Institute of Information Technology, University of Dhaka. This leaves us with construction validity as the result might be biased. 

### 5.2 Internal Validity
The cut-off rate or threshold used for this study is a source of threat to internal validity. As the project analyzed by this tool were known to the author of this study, the projects were analyzed multiple times by this tool with different threshold to find a good threshold value. Code smell detection largely depends on this threshold value. So, this is a threat to internal validity. Also understanding the requirements from base paper of this study is also a threat to internal validity. 

### 5.3 External Validity
In this study, some code smell types of different level and granularity has been considered. There might also be other types of code smells which can be generated by textual information analysis but was not considered in this study. This possesses a threat to external validity to our study.

## 6 Challenges
Understanding the paper, extracting textual data from project, building a generic corpus from project which can be used for each type of smell detection, calculating threshold were some of the main challenges.
