<img src="logo.png" alt="dbCalc">
<p><b>dbCalc</b> is a simple application written in Java. It is a functional dependency calculator created as to practise Database Theory: Schema Decomposition. This project is currently undergoing development.<br>
Current version: 0.55</p>

<h2>Overview</h2>
<p>dbCalc features a basic user interface, where functional dependencies can be added to a panel (representing FD sets). The utility can handle FD operations, as well as calculate minimal cover sets and closures for the FD set. The results appear in the respective text area or on the right hand side table.</p>

<h2>Background</h2>
<p>Functional dependencies (FD) are connections between a table's columns, where a certain column determines and therefore implies the other's values. While handy and most of the time essential, too many FD's can lead to repeating data values. Eliminating and simplifying FD's, so that they adhere to a series of conditions, (called normal forms) can reduce such redundancy.

dbCalc is intended to perform basic operations in schema planning, which the user may require.

<h2>Functionality</h2>
The current version supports:
<ul>
<li>Storage of functional dependencies.
<li>Calculation for an FD set's minimal cover.
<li>Calculation for an attribute set's closure.
<li>Evaluation for a given schema on a FD set's attributes.
</ul>

<h2>Motivation and Terms of Use</h2>
<p>dbCalc is intended to be a fun but basic free-to-use software, as well as free to anyone to try optimizing (and so I encourage you!) the source-code for the greater good. I want to make this software handy for getting know to and trying this utility in a "fun" field of Database Theory.</p>

<h2>Glossary</h2>
<ul>
<li><u>Relation</u>: A sheet/table of data represented with the column names.
<li><u>Attribute</u>: A member of a relation - a single column's name.
<li><u>Functional Dependency</u>: A connection between attribute sets, where the leftside set determines the rightside set's values. Formally: if A and B are members of the same relation ("r"), and said relation has two rows ("t" and "t'"), where the respective values of A and B are equal (t[A] = t'[A] and t[B] = t'[B]), then A determines B.
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
    <li>the rightside contains only one single attribute
    <li>the leftside can not be reduced further (the leftside's subsets do not imply each other through the existing FD's)
    <li>the FD itself cannot be omitted (the rightside is not implied by other FD's)
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

<h2>To be added...</h2>
<ul>
<li>Proper .pdf documentation for the utility.
<li>Key search.
<li>Normal form evaluation.
<li>And many more... 
</ul>

Dóka Zsolt (C) 2017
<hr>
