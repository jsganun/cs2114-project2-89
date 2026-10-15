# LibCatS
#- cs2114-project2-89: 
Library Catalogue System (LibCatS) is a cataloguing system that can be used by libraries to organize their collection of items efficiently. Through this system, users can browse available items, view item details, and search for specific items. Users can also check out and return items to and from the catalogue. The catalogue maintains a record of all of the items and updates when an action is done. Users interact with this catalogue by entering commands into a console.


# Compilation and Running the Program
...


# Reflection
## Specification Changes
...
## LinkedChain Changes
...
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
| ---       | ...               |
| Ajeet     | CheckoutRecord: (1) decide whether to keep the blank `CheckoutRecord()` constructor, which Revised Scope item 4 says to remove, and update CheckoutRecordTest to match; (2) align naming with the spec's Data & State (`id` vs. item `serial`) once Catalogue settles on one; (3) add tests that send blank and over-100-character borrower names through `Catalogue.borrowItem` and ConsoleMenu to confirm the backstop messages reach the user. |
| Jonathan  | ...               |
| Jacob     | ...               |
| Zayd      | ...               |


| Class                 | Who did what  | GenAI note    |
| ---                   | ---           | ---           |
| CheckoutRecord        | Ajeet Bondugula: wrote (GenAI-assisted). Added borrower and item validation in the constructor and setters, unique chronologically assigned IDs, `getSerial`, `equals`/`hashCode` by item serial, and `toString` | Drafted with Claude (Claude Code) from the spec's CheckoutRecord table, Invariants, and Where Validation Lives sections; reviewed by Ajeet |
| CheckoutRecordTest    | Ajeet Bondugula: wrote and tested (GenAI-assisted). 17 tests, with a normal case and a bad-input case for every public method, plus tests that store CheckoutRecords in `LinkedChain<CheckoutRecord>`; all pass | Drafted with Claude (Claude Code); reviewed and run by Ajeet |
| Catalogue             | ...           | ...           |
| CatalogueTest         | ...           | ...           |
| LibraryItem           | ...           | ...           |
| LibraryItemTest       | ...           | ...           |
| Book                  | ...           | ...           |
| BookTest              | ...           | ...           |
| Device                | ...           | ...           |
| DeviceTest            | ...           | ...           |
| DVD                   | ...           | ...           |
| DVDTest               | ...           | ...           |
| Game                  | ...           | ...           |
| GameTest              | ...           | ...           |
| Magazine              | ...           | ...           |
| MagazineTest          | ...           | ...           |