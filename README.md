# LibCatS
#- cs2114-project2-89: 
Library Catalogue System (LibCatS) is a cataloguing system that can be used by libraries to organize their collection of items efficiently. Through this system, users can browse available items, view item details, and search for specific items. Users can also check out and return items to and from the catalogue. The catalogue maintains a record of all of the items and updates when an action is done. Users interact with this catalogue by entering commands into a console.


# Compilation and Running the Program
...


# Reflection
## Specification Changes
The main changes that were made from specification were the implementation of additional methods that weren't initially noted in the specification document. Two examples that were added are `isValidID()` and `isValidSerial()`. These were added to assist in the data verification process to prevent any bad inputs from interfering with the program function.
## LinkedChain Changes
For our purposes, we never had to chain the LinkedChain class methods. Our cataloging system managed to use most of the methods in LinkedChain for its purposes. Any special functions, such as searching for a LibraryItem made by a creator, was implemented in Catalogue.
## Unknowns (what happened to what we didn't know how to do)
...
## What Doesn't Work Yet


# Next Steps
## Remaining Work
...
## Stretch Goals
...
### Committed
...
### Dropped
...
## New Tests
...
## Responsibilities
| Member    | Remaining Work    |
| ---       | ---               |
| Ajeet     | CheckoutRecord: (1) decide whether to keep the blank `CheckoutRecord()` constructor, which Revised Scope item 4 says to remove, and update CheckoutRecordTest to match; (2) align naming with the spec's Data & State (`id` vs. item `serial`) once Catalogue settles on one; (3) add tests that send blank and over-100-character borrower names through `Catalogue.borrowItem` and ConsoleMenu to confirm the backstop messages reach the user. |
| Jonathan  | ...               |
| Jacob     | ...               |
| Zayd      | ...               |


| Class                 | Who did what  | GenAI note    |
| ---                   | ---           | ---           |
| CheckoutRecord        | <strong>Ajeet Bondugula</strong>: Added borrower and item validation in the constructor and setters, unique chronologically assigned IDs, `getSerial`, `equals`/`hashCode` by item serial, and `toString`<br><br><strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Drafted from the spec's CheckoutRecord table, Invariants, and Where Validation Lives sections; reviewed by Ajeet <br><br> Boilerplate generated using Microsoft Copilot |
| CheckoutRecordTest    | <strong>Ajeet Bondugula</strong>: wrote and tested 17 tests, with a normal case and a bad-input case for every public method, plus tests that store CheckoutRecords in `LinkedChain<CheckoutRecord>`(Gen AI assisted here); all pass | Drafted from the spec's CheckoutRecord method table (normal and bad cases) and Where Validation Lives section. Normal and bad-input cases were refined via Claude Code.
| Catalogue             | ...           | ...           |
| CatalogueTest         | ...           | ...           |
| LibraryItem           | <strong>Jonathan Ganun</strong>: Created the boilerplate and JavaDocs comments using AI. Implemented all of the methods by hand.| Used Micorosoft Copilot to generate a boilerplate with JavaDocs comments.|
| LibraryItemTest       | <strong>Jonathan Ganun</strong>: Created the boilerplate and JavaDocs comments using AI. Implemented all of the methods by hand. Used AI to debug issues with testing.| Used Microsoft Copilot to generate a biolerplate with JavaDocs comments and debug issues with tests. |
| Book                  | <strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Used Microsoft Copilot to generate the boilerplate. |
| BookTest              | ...           | ...           |
| Device                | <strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Used Microsoft Copilot to generate the boilerplate. |
| DeviceTest            | ...           | ...           |
| DVD                   | <strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Used Microsoft Copilot to generate the boilerplate. |
| DVDTest               | ...           | ...           |
| Game                  | <strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Used Microsoft Copilot to generate the boilerplate. |
| GameTest              | ...           | ...           |
| Magazine              | <strong>Jonathan Ganun</strong>: Created the boilerplate using AI. | Used Microsoft Copilot to generate the boilerplate.  |
| MagazineTest          | ...           | ...           |
