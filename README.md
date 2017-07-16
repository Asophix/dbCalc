<img src="logo.png" alt="dbCalc">
<p><b>dbCalc</b> is a simple application written in Java. It is a functional dependency calculator created as to practise Database Theory: Schema Decomposition. This project is currently complete.<br>
Current version: 1.0</p>

<h2>Overview</h2>
<p>dbCalc is a lightweight utility that works with functional dependencies (FD's). FD's are logical relationships, where a set of columns' values determine another set's values, due to their logical implications. Such dependencies are necessary to regulate value input in the table, however overusing them can lead to multiple inclusion of the same value, unnecessarily enlarging data quantity and table size. There are a multitude of principles that can be used to measure redundancy and to reduce the overall number of occurring dependencies:</p>
<ul>
<li><u>Normal forms:</u> The generic measure for FD-based redundancy. Respective normal forms require a table to follow a number of requirements, therefore including a relative quantity of redundancy (compared to one normal form or another). FD-based redundancies can range from 0NF to BCNF (see glossary). dbCalc works with at least 1NF compositions.</li>
<li><u>Key attributes:</u> Some columns are more significant than others due to their role in determining other columns. These key attributes are mostly the source of compulsory dependencies and therefore determine the overall "sluggishness" of the database. Due to this fact however, their presence can effectively reduce redundancy.</li>
<li><u>Closures:</u> Closures are basically an implication of all attributes or FD's, concluding whether an attribute set or FD set has a significant "key" role. Finding these components helps us to find keys and redundancy-free resolutions easier.</li>
<li><u>Resolutions:</u> Most of the time, splitting up the initial table is the best choice to eliminate redundancies. While this can be committed at the expense of more data, having more transparent tables can make future queries run faster and easier. In order to not lose information, the requirements of being "lossless" and "dependency-preserving" are declared in practice.</li>
</ul>
<h2>Functionality</h2>
The current version supports:
<ul>
<li>Storage of functional dependencies.
<li>Calculation for an FD set's minimal cover.
<li>Calculation for an attribute set's closure.
<li>Evaluation for a given schema on a FD set's attributes.
<li>Finding keys in an FD set.
<li>Calculating a highest normal form on a relation and FD set (up to BCNF).
</ul>
<h2>Motivation and Terms of Use</h2>
<p>dbCalc is intended to be a fun but basic free-to-use software, as well as free to anyone to try optimizing (and so I encourage you!) the source-code for the greater good. I want to make this software handy for getting know to and trying this utility in a "fun" field of Database Theory.</p>
<h2>Glossary</h2>
<ul>
<li><u>Relation</u>: A sheet/table of data represented with the column names.
<li><u>Attribute</u>: A member of a relation - a single column's name.
<li><u>Functional Dependency</u>: A connection between attribute sets, where the left side set determines the right side set's values. Formally: if A and B are members of the same relation ("r"), and said relation has two rows ("t" and "t'"), where the respective values of A and B are equal (t[A] = t'[A] and t[B] = t'[B]), then A determines B.
<li><u>Key</u>: An attribute set that implies all attributes in the relation and cannot be reduced further (formally, no power set of the key implies all attributes).
<li><u>Superkey</u>: An attribute set that implies all attributes. In other words: an attribute set that contains a key.
<li><u>Primary attribute</u>: A member attribute of a key.
<li><u>Secondary attribute</u>: An attribute that is not contained by any key.
<li><u>Closure</u>:
  <ul>
  <li>Attribute set's closure: all attributes that can be implied through a given FD set.
  <li>FD set's closure: all dependencies that can be implied through a given FD set and with the following rules (Armstrong-axioms):
    <ul>
    <li>if B is a subset of A, then A->B is true
    <li>if A->B and B->C are true, then A->C is true
    <li>if A->B is true, then AC->BC is true for any C
    </ul>
  </ul>
<li><u>Minimal cover</u>: A FD set's reduced form, in which all FD conforms the following: 
  <ul>
    <li>the right side contains only one single attribute
    <li>the left side can not be reduced further (the leftside's subsets do not imply each other through the existing FD's)
    <li>the FD itself cannot be omitted (the right side is not implied by other FD's)
   </ul>
<li><u>Normal form</u>: a means of measuring redundancy.
  <ul>
    <li>0NF: relation contains non-atomic (repeating or grouped) attributes.
    <li>1NF: relation contains only atomic attributes.
    <li>2NF: secondary attribute is only determined by superkey.
    <li>3NF: 1NF and in all A->B FD: either A is a key, or B is a primary attribute.
    <li>BCNF (Boyce-Codd NF): 1NF and in all A->B FD: A is a superkey. BCNF forms contain no FD-based redundancy.
   </ul>
</ul>

Dóka Zsolt (C) 2017
<hr>
