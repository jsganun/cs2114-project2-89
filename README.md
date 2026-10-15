# LibCatS
Library Catalogue System (LibCatS) is a cataloguing system that can be used by libraries to organize their collection of items efficiently. Through this system, users can browse available items, view item details, and search for specific items. Users can also check out and return items to and from the catalogue. The catalogue maintains a record of all of the items and updates when an action is done. Users interact with this catalogue by entering commands into a console.

Group 89 (cs2114-project2-89). Project 2, Deliverable 3: Prototype + Tests.

**Status:** the program runs on the MVP path with a console menu, one generic `LinkedChain<T>` does all the storage, and **236 JUnit tests pass**. Two scope stretch goals work (extra descriptors with validation, and a waitlist).

---

# Compilation and Running the Program

Run these from the repository root (the folder that contains `Classes/`, `Tests/`, and `lib/`). Java 11 or newer is needed (the code uses `String.repeat`; we checked that it compiles with `--release 11`).

**Compile** the program and the tests into a folder called `out`:

```bash
mkdir -p out
javac -d out -cp lib/junit-platform-console-standalone-1.10.2.jar Classes/*.java Tests/*.java
```

**Run the program** (it starts with a small sample catalogue of 15 items, 5 of them checked out):

```bash
java -cp out Classes.Main
```

**Run all the tests:**

```bash
java -jar lib/junit-platform-console-standalone-1.10.2.jar -cp out --scan-classpath
```

**In Eclipse:** the project already has `.classpath` set up with the JUnit jar. Run `Classes/Main.java` as a Java Application, and run the `Tests` package with *Run As > JUnit Test*.

## Using the menu

```
=== LibCatS Library Catalogue ===
[1]  Show all items                    [7]  Add an item
[2]  Show available items              [8]  Remove an item
[3]  Show checked-out items            [9]  Check out an item
[4]  Show checkout records             [10] Return an item
[5]  View details of one item          [11] Join the waitlist for a checked-out item
[6]  Search                            [12] Show recent activity
[0]  Exit
```

Items are found by **serial number** (shown in square brackets in the lists). Search can look by title, creator, serial number, or *any attribute* (genre, platform, description, ISBN, and so on). Press Ctrl+D at any prompt to leave cleanly.

---

# Reflection

Where a change came from is tagged: **[Spec]** the written specification, **[Scope]** our Deliverable 1 scope, **[Group]** group discussion, **[Claude Code]** drafted or made by Claude Code (GenAI) while revising the prototype at Ajeet's request after the teammates' first build was pushed, and **[Own]** a teammate's own reflection while building.

## Specification Changes

The first build was written from the per-class method tables in the spec (the second half). It did not follow the front half of the spec (Class Design, Invariants, Where Validation Lives, Revised Scope), so it contradicted six things the spec says we decided. This revision brings the code in line with the spec, and a few places where the spec contradicted itself or the scope were resolved as described.

| What the spec said | What the first build had | What we have now, and why |
| --- | --- | --- |
| Revised Scope 1: `LibraryItem` is **abstract**. | A concrete class; tests did `new LibraryItem(...)`. | `public abstract class LibraryItem` with an abstract `getType()`. The five subclasses extend it. Why: the shared title, creator, serial and `equals` are written once. **[Spec, Group]**, built by **[Claude Code]**. |
| Revised Scope 2: `ConsoleMenu` is the only class that reads or prints; `Main` only starts the program. | `Main` held half-written static menu code with the loop switched off (`running = false`). | New `ConsoleMenu(Catalogue, Scanner, PrintStream)` with the loop, every command and all input checks. `Main` builds the sample catalogue and calls `run()`. Jonathan's sample catalogue and list layouts were kept. Because input and output are passed in, `ConsoleMenuTest` can type a script of lines. **[Spec, Group]**, built by **[Claude Code]**. |
| Revised Scope 3: loans are a chain of `CheckoutRecord(serial, borrower)`; the `record` field is removed from `LibraryItem`; an item is out if and only if a record with its serial exists. | `LibraryItem` had a `record` field **and** `CheckoutRecord` had an `id` and an `item`: two sources of truth. | `LibraryItem.record`, `getRecord` and `setRecord` are gone. `CheckoutRecord` holds `final int serial` and `borrower`. `Catalogue` answers "is it out?" by looking for the record (`isCheckedOut`, `getBorrower`). `getCheckoutRecord(int)` now takes a serial. **[Spec]**, built by **[Claude Code]**. |
| Revised Scope 4: no no-argument constructors. | All 5 subclasses, `LibraryItem` and `CheckoutRecord` had one (and a test expected "N/A" output from a blank item). | All removed. A blank item or record can no longer exist. **[Spec]**, built by **[Claude Code]**. |
| Revised Scope 5: subclass constructors take title and creator first. | Subclasses already took title, creator, description, then their descriptors, but never checked any of them. | Same parameter order. Every constructor and setter now validates: title and creator are non-blank and at most 100 characters; description may be empty but not null; String descriptors are non-blank; counts (`pageCount`, `numEpisodes`) are greater than 0. A rejected setter keeps the old value. **[Spec]**, built by **[Claude Code]**. The spec's constructor table omits `description`; we kept it because the spec's Data and State section lists it as an attribute. |
| Where Validation Lives: three layers (menu re-prompts, domain classes backstop, `LinkedChain` guards storage). | No menu layer. `LibraryItem` accepted blank and 150-character text. | Layer 1 is `ConsoleMenu.readNonBlank`, `readInt` and `readPositiveInt`. Layer 2 is the constructors and setters above, `CheckoutRecord`, `WaitlistEntry` and `Catalogue`. Layer 3 is `LinkedChain`. See the bad-input table below. **[Spec]**, built by **[Claude Code]**. |
| Response convention: a broken rule throws `IllegalStateException` with a message the menu prints as written. The Catalogue method table said `borrowItem` and `returnItem` return false for the same case. | `borrowItem` and `returnItem` returned false, so the user was never told who has the item. | The spec contradicts itself, so we followed the convention and the scope ("the program shows who has it"). `borrowItem` and `returnItem` throw `IllegalStateException` ("Item 3 is already checked out to Sam." / "Item 1 is not checked out.") and return `false` only when the item is not in this catalogue. They still return `boolean`, so the signature in the spec's table is unchanged. **[Spec, Scope]**, decided by **[Claude Code]**; please confirm as a group. |
| Catalogue assigns serials from `nextSerial` (starts at 1), never reused; 0 means "not yet assigned". | Serials came from a static counter inside `LibraryItem` that started at 0 and counted every item ever created, even ones never added. | `Catalogue.addItem` assigns the serial through a package-private `LibraryItem.assignSerial`, so tests and menu code cannot set or change a serial. `addItem` now returns the serial. Adding the same object twice throws `IllegalStateException`. **[Spec]**, built by **[Claude Code]**. |
| Invariant: "An item with a record cannot be removed." (and the scope: "refused until it is returned"). | `removeItem` removed it anyway and left an orphaned record. A later change by Jonathan made `removeItem` quietly return the item first, which silently ended someone's loan. | `removeItem` throws `IllegalStateException("Item 3 is checked out to Sam and cannot be removed until it is returned.")`. It returns `false` if the item is not there, and clears that item's waitlist when it is removed. **[Spec, Scope]**, built by **[Claude Code]**. |
| `getItemsByTitle` and `getItemsByCreator` are O(n). | They called `items.get(i)` in a loop. `LinkedChain.get` is O(n), so they were O(n squared) (measured 198 ms at 16,000 items). They also crashed with a `NullPointerException` on an item with a null title. | Both copy the chain once with `toArray` and scan the array: O(n). Matching ignores case. A null or blank argument throws `IllegalArgumentException`. **[Own]** (found while checking the Big-O estimates), fixed by **[Claude Code]**. |
| (Not in the spec.) Jonathan's `getAllReturnedItems` and `getAllBorrowedItems`. | They used `java.util.ArrayList` and cast `toArray()` to `LibraryItem[]`, which throws `ClassCastException` the first time it runs. | Rewritten to store results in a `LinkedChain` and use `toArray(new LibraryItem[...])`, so the chain still does the storage. The names `getAllItems`, `getAllReturnedItems` (items that are available), `getAllBorrowedItems` and `getAllCheckoutRecords` are Jonathan's and were kept. **[Group]** (Jonathan's methods), fixed by **[Claude Code]**. |
| (Not in the spec.) `isValidID()` and `isValidSerial()` helpers. | Private helpers in `Catalogue` that scanned the chains with `get(i)`. | Removed. `getItem` and `getCheckoutRecord` throw `IndexOutOfBoundsException("No such item: N")` for any serial that matches nothing (including 0 and negatives), which the scope asks for. **[Claude Code]**. |
| (Not in the spec.) | n/a | New methods: `Catalogue.searchItems` (the scope's "search by other attributes"), `isCheckedOut`, `getBorrower`, and the waitlist methods; `LibraryItem.matches`, `summary`, `getType`; `Book.setIsbn`, `Magazine.setDateOfIssue`; new class `WaitlistEntry`. These come from the scope's MVP and stretch goals. **[Scope]**, built by **[Claude Code]**. |
| Test plan: a normal case and a bad-input case for every public method. | The five subclass tests had no bad-input tests, `LinkedChain` had no test file, and nothing tested the menu. | Added bad-input tests for every subclass constructor and setter, `LinkedChainTest`, `WaitlistEntryTest`, `ConsoleMenuTest`, `MainTest`; `CatalogueTest` and `CheckoutRecordTest` were rewritten for the new rules. **[Spec]**, built by **[Claude Code]**. |
| `setCheckoutRecord` "throws InvalidClassException" for a wrong type; setters "keep the old value if not a String". | n/a | Java's types already stop a wrong type at compile time, and `InvalidClassException` is about file serialization, so neither is testable. We test the real bad inputs instead (null, blank, too long, not positive). The field itself no longer exists (Revised Scope 3). **[Own]**. |

## LinkedChain Changes

We changed the provided class in two ways, both promised in the spec (Data and State, and The Generic Contract), and used everything else unchanged. **[Spec, Group]**, implemented by **[Claude Code]**.

1. **Tail reference.** We added `private Node<T> lastNode`. `add` sets it when the chain was empty, `addToEnd` links onto it instead of walking the chain, and `remove()`, `remove(T)` and `clear()` keep it correct (removing the last entry moves it back, removing the only entry sets it to null). This makes `addToEnd` **O(1) instead of O(n)**, which matters because `Catalogue` appends on every add. The class comment and the Big-O list were updated to match. `LinkedChainTest.testRemoveEntryFirst`, `testRemoveEntryLast` and `testRemoveEntryOnly` test the three cases that can break the tail, one by one, as the scope asks.
2. **`add(null)` and `addToEnd(null)` return `false`** and leave the chain unchanged, instead of throwing. This is what the spec's Generic Contract promises. The private `requireNonNull` helper was removed. `Catalogue.addItem(null)` still throws `IllegalArgumentException("Item cannot be null")` so a null is never silently dropped.

Unchanged: `get` is still O(n). The spec's method table says O(1), which is wrong for a singly linked chain, so the estimate in the table was not met and we instead made `Catalogue` copy a chain to an array once with `toArray` before scanning it. `Catalogue` and `ConsoleMenu` use the chain only through its public methods (`add`, `addToEnd`, `remove(T)`, `contains`, `size`, `toArray`); `get`, `count`, `clear`, `isEmpty`, `remove()` and `toString` are not needed by the application and are covered by `LinkedChainTest`.

### How the one generic class is reused

| Chain | Element type | Where | What it holds |
| --- | --- | --- | --- |
| `LinkedChain<LibraryItem>` | `LibraryItem` | `Catalogue.items` | `Book`, `Magazine`, `DVD`, `Device` and `Game` objects, together, in the order added |
| `LinkedChain<CheckoutRecord>` | `CheckoutRecord` | `Catalogue.records` | one record per loan |
| `LinkedChain<WaitlistEntry>` | `WaitlistEntry` | `Catalogue.waitlist` | people waiting for checked-out items, in order joined |
| `LinkedChain<String>` | `String` | `ConsoleMenu.history` | recent actions (`add` puts the newest at the front, so `toArray` returns newest first) |

That is **four different element types, plus five concrete item classes in one chain, through the same class with no type-specific copies.** Tests: `MainTest.testCreateSampleCatalogue` (five item types in one chain), `CatalogueTest` (mixed items), `LinkedChainTest.testManyElementTypes`.

## Unknowns (what happened to what we didn't know how to do)

| Unknown from our scope | Outcome |
| --- | --- |
| Abstract class or interface for the item types? | **Figured out: abstract class.** An interface cannot hold the shared title, creator and serial, so five subclasses would repeat them, and an abstract class lets `equals` and validation be written once. `getType()` is the abstract method; each subclass overrides it, `toString` and `searchText`. **[Group]** decision; **[Claude Code]** was asked the tradeoff question and implemented it. |
| How to write `equals()` so removal works when two copies share a title? | **Figured out.** `LibraryItem.equals` and `hashCode` use the serial only (an item with no serial yet equals only itself), and `CheckoutRecord.equals` uses the serial. `CatalogueTest.testRemoveItemOneOfTwoCopies` removes one of two copies of the same book and checks the other is untouched. The first build overrode `equals` without `hashCode`; fixed. |
| How to handle bad input and a closed input stream with `Scanner`, including `InputMismatchException`? | **Figured out.** `ConsoleMenu` only reads with `hasNextLine` and `nextLine` and converts numbers with `Integer.parseInt`, catching `NumberFormatException`, so there is no `InputMismatchException` and no bad token left in the buffer. A closed or empty stream (or a closed `Scanner`) becomes a private `EndOfInput` exception that `run()` turns into "Goodbye." and a normal return. Covered by `testNonNumericChoice`, `testRunEmptyInputStream`, `testRunClosedScanner`, `testRunInputEndsMidCommand`. |
| How to split the program so the menu code stays separate from the chain? | **Figured out.** `LinkedChain` stores only; `Catalogue` holds the rules; `LibraryItem` and the subclasses describe themselves; `CheckoutRecord` and `WaitlistEntry` hold single facts; `ConsoleMenu` is the only class that reads or prints; `Main` wires them together. Owners are in the Responsibilities table. |
| Biggest risk: getting `LinkedChain` right. | **Handled.** `LinkedChainTest` (28 tests) covers every public method, with the first, last and only entry removed one by one. It found no bugs in the provided chain, and the tail reference we added passes the same tests. |

**Still open:** saving and loading a file, and due dates, are scope stretch goals we did not build (see Next Steps). The scope says `LocalDate` is "in the java.lang package"; it is actually `java.time.LocalDate`, so the import will be different when we build due dates.

## What Doesn't Work Yet

* **Not built:** the stretch goals "save and load the catalogue to a file" and "due dates with an overdue report". The catalogue resets to the sample data every time the program starts.
* There is **no menu command to edit an item** after it is added. The setters exist and are tested, but the menu cannot reach them, so a mistyped ISBN or genre can only be fixed by removing and re-adding the item.
* `Book` has an ISBN but not the `publisher` the scope mentions; `Magazine` has a date of issue but not the "type of information" (Lifestyle, Sports) beyond `genre`.
* **Searching by title or creator needs the whole title or name** (ignoring case). Partial words are found through "any attribute" search (option 6, then 4).
* **Speed:** the lists in options 1 to 3 and `getAllReturnedItems` / `getAllBorrowedItems` are O(n times r), because each item looks for its record in the records chain. That is fine at library scale but not O(n).
* `Catalogue` decides "is this item in the catalogue" with `items.contains`, which compares serials. This is safe with one `Catalogue` per program (as in `Main`), but an item from a second `Catalogue` that has the same serial would be treated as present.
* `ConsoleMenu` prints prompts without a newline, so output captured from piped input looks run together (it looks normal in a terminal).
* The spec's UML shows no `WaitlistEntry`, `searchItems`, or `ConsoleMenu`; the spec document should be updated to match this README before the final.

---

# Big-O of the application methods

n = items, r = records (loans), w = waitlist entries, k = people waiting for one item (usually 0). All timings below are from a throwaway benchmark (not committed) on 5,000 to 40,000 items: adding 40,000 items took about 1 ms in total; `getItemsByTitle` on 40,000 items took about 0.12 ms and did not grow faster than the item count. For comparison, the first build's `getItemsByTitle` took 198 ms at only 16,000 items because it was O(n squared).

| Method | Spec estimate | Actual | Matches the spec? | Reason |
| --- | --- | --- | --- | --- |
| `Catalogue()` | O(1) | O(1) | Yes | creates three empty chains |
| `addItem` | (not listed) | O(1) | n/a | assigns a serial and `addToEnd` is O(1) with the tail reference |
| `removeItem` | (not listed) | O(n + r + w + k·w) | n/a | `contains`, find the record, then remove each waiting entry |
| `getAllItems` | (not listed) | O(n) | n/a | one `toArray` |
| `getAllReturnedItems`, `getAllBorrowedItems` | (not listed) | O(n·r) | n/a | each item scans the records snapshot |
| `getItemsByTitle`, `getItemsByCreator` | O(n) | O(n) | **Yes** (after the fix; the first build was O(n²)) | one `toArray`, then one scan |
| `searchItems` | (not listed) | O(n) for text of bounded length | n/a | one scan, `matches` looks at a few short strings |
| `getItem` | O(n) | O(n) | Yes | one scan |
| `getCheckoutRecord`, `getCheckoutRecords` | O(n) | O(r) | Yes (r ≤ n, so it is tighter) | one scan of the records |
| `getAllCheckoutRecords`, `isCheckedOut`, `getBorrower` | (not listed) | O(r) | n/a | one scan of the records |
| `borrowItem` | **O(1)** | O(n + r + w) | **No** | the spec assumed an `item.record` field; Revised Scope 3 removed it, so we must check `items.contains`, search the records for a loan, and check the waitlist |
| `returnItem` | O(n) | O(n + r), which is O(n) because r ≤ n | Yes | `contains`, find the record, `remove` |
| `joinWaitlist` | (not listed) | O(n + r + w) | n/a | same checks as `borrowItem`, plus a `contains` on the waitlist |
| `getWaitlist`, `getNextInLine` | (not listed) | O(w) | n/a | one scan of the waitlist |
| `LibraryItem` getters and setters, subclass getters and setters, `CheckoutRecord` and `WaitlistEntry` methods | O(1) | O(1) | Yes | fixed number of fields; text checks look at one short string |
| `LibraryItem.matches`, `toString`, `summary` | (not listed) | O(L), L = text length | n/a | build or search a short string |
| `Book.setIsbn`, `Magazine.setDateOfIssue` | (not listed) | O(1) | n/a | at most 13 characters; one date parse |
| `LinkedChain.add`, `remove()`, `clear`, `size`, `isEmpty` | O(1) | O(1) | Yes | |
| `LinkedChain.addToEnd` | O(n) in the spec table, O(1) in the spec's Data and State | **O(1)** | Matches the Data and State section | tail reference |
| `LinkedChain.remove(T)`, `contains`, `count`, `toArray`, `toString` | O(n) | O(n) | Yes | |
| `LinkedChain.get` | **O(1)** | O(n) | **No** | the spec is wrong for a singly linked chain; the provided class says O(n) |
| `ConsoleMenu` commands | (not listed) | each one is dominated by the Catalogue call it makes; the three list commands are O(n·r) | n/a | |

---

# Bad input

Each case from the scope gets the response the spec's Where Validation Lives section names, and each has a test.

| Bad input (from the scope) | Caught by | Response | Test |
| --- | --- | --- | --- |
| Blank or whitespace-only title or creator on add | `ConsoleMenu.readNonBlank`; backstop `LibraryItem` constructor and setters | Prints "Text cannot be blank. Please try again." and asks again. Backstop: `IllegalArgumentException("Title cannot be blank")` | `ConsoleMenuTest.testAddBadInputIsReprompted`, `LibraryItemTest.testConstructorBadInput` |
| Menu choice or serial that is not a number | `ConsoleMenu.readInt` | Prints "That is not a number. Please try again." and asks again | `testNonNumericChoice`, `testShowDetailsBadInput` |
| A number that is not on the menu | `ConsoleMenu.carryOut` | "Please choose a number from the menu." | `testUnknownChoice` |
| Serial that matches no item (including 0 and negative) | `Catalogue.getItem` | `IndexOutOfBoundsException("No such item: 0")`, printed by the menu | `CatalogueTest.testGetItemBadInput`, `ConsoleMenuTest.testShowDetailsBadInput` |
| Checking out an item that is already out | `Catalogue.borrowItem` | `IllegalStateException("Item 3 is already checked out to Sam.")`; no record added | `CatalogueTest.testBorrowItemAlreadyOut`, `ConsoleMenuTest.testBorrowItemAlreadyOut` |
| Returning an item that is not checked out | `Catalogue.returnItem` | `IllegalStateException("Item 1 is not checked out.")`; both chains unchanged | `testReturnItemNotOut` (both test classes) |
| Removing an item that is checked out | `Catalogue.removeItem` | `IllegalStateException("... cannot be removed until it is returned.")` | `testRemoveItemCheckedOut` (both test classes) |
| Search with no matches | `Catalogue` search methods; `ConsoleMenu.search` | empty array; the menu prints "Nothing found." | `ConsoleMenuTest.testSearchNothingFound`, `CatalogueTest.testGetItemsByTitle` |
| Title, creator or borrower over 100 characters | `ConsoleMenu.readNonBlank`; backstops in `LibraryItem`, `CheckoutRecord`, `WaitlistEntry` | "Text is over 100 characters. Please try again." Backstop: `IllegalArgumentException("Title is over 100 characters")` | `testAddBadInputIsReprompted`, `testAddOverlongCreator`, `testBorrowItemBadInput` |
| Very long garbage or special characters | by design, not a check: lines are read whole with `nextLine`, printed with `println`, compared with plain `String` methods, never used as a format or pattern | treated as ordinary text, no crash | `ConsoleMenuTest.testGarbageInput`, `CatalogueTest.testSearchItemsBadInput`, `LibraryItemTest.testMatchesBadInput` |
| Closed or empty input stream (Ctrl+D) | `ConsoleMenu.readLine` (private `EndOfInput`) | prints "Goodbye." and returns normally | `testRunEmptyInputStream`, `testRunClosedScanner`, `testRunInputEndsMidCommand`, `MainTest.testMainEmptyInput` |
| Rapid repeated adds of the same item | allowed; `Catalogue.addItem` | each copy gets a new unique serial | `testRapidRepeatedAdds`, `CatalogueTest.testAddItemDuplicateCopies` |
| Remove from an empty chain, or remove an item that is not present | `LinkedChain.remove(T)`; `Catalogue.removeItem` | returns `false`, chain unchanged | `LinkedChainTest.testRemoveEntryBadInput`, `CatalogueTest.testRemoveItemNotThere` |
| Add null | `LinkedChain.add` and `addToEnd`; `Catalogue.addItem` | the chain returns `false` and is unchanged; `Catalogue.addItem(null)` throws `IllegalArgumentException("Item cannot be null")` | `LinkedChainTest.testAddBadInput`, `testAddToEndBadInput`, `CatalogueTest.testAddItemBadInput` |
| Get an index out of range | `LinkedChain.get` | `IndexOutOfBoundsException` | `LinkedChainTest.testGetBadInput` |
| Search an empty chain | `LinkedChain.toArray`; `Catalogue` search methods | empty array | `CatalogueTest.testConstructorEmptyCatalogue` |
| Remove the first, last and only item | `LinkedChain.remove(T)` | chain stays correct | `LinkedChainTest.testRemoveEntryFirst`, `testRemoveEntryLast`, `testRemoveEntryOnly` |
| Anything unexpected | `ConsoleMenu.run` wraps every command | prints "Something went wrong. Returning to the menu." and keeps running | `ConsoleMenuTest.testUnexpectedExceptionKeepsRunning` |

---

# Next Steps

The MVP from the scope is finished and tested (add and remove items with title, creator and serial; summary with availability; details of one item; search by title, serial or other attributes; separate available and checked-out categories; who has each item). What is left is polish, the stretch goals we commit to, and getting every teammate ready for the grading session.

## Remaining Work
In the order we will do it:

1. **Everyone:** read and be able to explain the code you own, run the tests, and run the program once, before the grading session (this revision changed every class). Owners are in the table below.
2. **Zayd:** add the missing scope descriptors: `Book.publisher` and a Magazine "type of information" check, using the same validation pattern as `genre`. Add a constructor-style bad-input test for each.
3. **Jonathan:** add a menu command "Edit an item" (change title, creator, description, or a descriptor) that calls the existing validated setters and re-prompts on bad input.
4. **Ajeet:** add a due date to `CheckoutRecord` (a `java.time.LocalDate`, validated as a real date, default 14 days after checkout) so the overdue report can work.
5. **Jacob:** add `Catalogue.getOverdueRecords(LocalDate today)` and make the "available / checked out" categories O(n + r) if time allows.
6. **Jonathan:** add the "Overdue report" menu command.
7. **Everyone:** update the spec document and UML to include `ConsoleMenu`, `WaitlistEntry`, `searchItems`, the due-date methods, and the Big-O table, then re-run all tests and the Big-O table before the final.

## Stretch Goals
From the four in our scope:

### Committed
* **Extra descriptors with validation** (ISBN for books with the check-digit rule, date of issue for magazines checked for being a real date): **already built and working** (`Book.setIsbn`, `Book.isValidIsbn`, `Magazine.setDateOfIssue`, `Magazine.isValidDate`; add flow in the menu asks for them and re-prompts on bad input).
* **Waitlist for checked-out items** (a queue of waiting borrowers per item and a notice to the next person on return): **already built and working** (`WaitlistEntry`, `Catalogue.joinWaitlist`, `getWaitlist`, `getNextInLine`; menu option 11; the notice is printed when an item is returned; the first person in line has the item held for them).
* **Due dates with an overdue report:** committed for the final (steps 4 to 6 above).

### Dropped
* **Save and load the catalogue to a file.** Reading and parsing a file correctly and handling a missing or corrupted file is the largest of the four goals, and the catalogue is rebuilt from sample data each run. We are dropping it so we have time to make the other three solid.

## New Tests
The added work must be covered by these tests (the names we will use):

| Work | Normal case | Bad-input case |
| --- | --- | --- |
| `Book.publisher` | `BookTest.testSetPublisher` | `BookTest.testSetPublisherBadInput` (null, blank) |
| Magazine "type of information" | `MagazineTest.testSetInfoType` | `MagazineTest.testSetInfoTypeBadInput` |
| Edit an item (menu) | `ConsoleMenuTest.testEditItem` | `ConsoleMenuTest.testEditItemBadInput` (blank, over 100, serial that matches nothing) |
| Due date | `CheckoutRecordTest.testGetDueDate`, `testSetDueDate` | `CheckoutRecordTest.testSetDueDateBadInput` (null, a date before checkout) |
| Overdue records | `CatalogueTest.testGetOverdueRecords` | `CatalogueTest.testGetOverdueRecordsNoneOverdue`, `testGetOverdueRecordsBadInput` (null date), empty catalogue |
| Overdue report (menu) | `ConsoleMenuTest.testShowOverdueReport` | `ConsoleMenuTest.testShowOverdueReportNoneOverdue` |

## Responsibilities
Owners follow the Division of Work in our specification (Ajeet: CheckoutRecord; Jacob: Catalogue; Jonathan: LibraryItem; Zayd: the five subclasses), with the menu and `Main` going to Jonathan because he wrote `Main`.

| Member    | Remaining Work    |
| ---       | ---               |
| Ajeet     | Step 1. Step 4: due date in `CheckoutRecord`, with `CheckoutRecordTest.testGetDueDate`, `testSetDueDate`, `testSetDueDateBadInput`. Step 7: README and spec update (Reflection, Big-O). The three items from the earlier plan are **done**: the blank `CheckoutRecord()` constructor was removed, the record now holds `serial` (not `id` plus item), and blank and over-100-character borrower names are tested through `Catalogue.borrowItem` and the menu (`CatalogueTest.testBorrowItemBadInput`, `ConsoleMenuTest.testBorrowItemBadInput`). |
| Jonathan  | Step 1. Step 3: "Edit an item" command and tests. Step 6: "Overdue report" command and tests. Owns `LibraryItem`, `ConsoleMenu` and `Main`. |
| Jacob     | Step 1. Step 5: `Catalogue.getOverdueRecords`, with its tests, and the O(n + r) categories. Owns `Catalogue` and `CatalogueTest`. |
| Zayd      | Step 1. Step 2: `Book.publisher` and the Magazine "type of information", with the tests above. Owns the five subclasses and their tests. |

---

# Attribution

Teammate contributions come from the git history and the earlier version of this table. **"Refined by Claude Code"** marks the work done in the revision pass described in the GenAI note below; it says specifically what was changed. Teammates: please read your rows and correct anything that is wrong before submitting.

| Class / test file | Who did what | GenAI note |
| --- | --- | --- |
| LinkedChain | <strong>CS 2114 Staff</strong>: wrote the provided class.<br><br><strong>Refined by Claude Code</strong>: added the `lastNode` tail reference (and kept it correct in `remove()`, `remove(T)` and `clear()`), made `add(null)` and `addToEnd(null)` return `false` instead of throwing, removed the `requireNonNull` helper, updated the class comment and Big-O list (`addToEnd` is now O(1)). Reviewed by Ajeet. | Claude Code, from the spec's Data and State and Generic Contract sections. |
| LinkedChainTest | <strong>Written by Claude Code</strong> (28 tests): a normal and a bad-input case for every public method, the first, last and only entry removed one by one, add after remove, duplicates, and several element types. Reviewed by Ajeet. | Claude Code, from the scope's "On the linked chain itself" list. |
| LibraryItem | <strong>Jonathan Ganun</strong>: created the boilerplate and JavaDocs with AI; implemented the methods by hand (title, creator, description, serial, `equals`, `toString`).<br><br><strong>Refined by Claude Code</strong>: made the class `abstract` with an abstract `getType()`; removed the no-argument constructor, the static serial counter, and the `record` field with `getRecord` and `setRecord`; added validation (title and creator non-blank and at most 100 characters, description not null) to the constructor and setters; added `matches`, `searchText`, `summary`, `hashCode` (the first version overrode `equals` without it), and the package-private `assignSerial`; `equals` now compares serials and treats an item with no serial as equal only to itself; `toString` now starts with the type and serial. | Microsoft Copilot generated the original boilerplate; Claude Code made the revision from the spec's Revised Scope 1, 3 and 4 and Invariants. |
| LibraryItemTest | <strong>Jonathan Ganun</strong>: created the boilerplate and JavaDocs; implemented the tests by hand; used AI to debug.<br><br><strong>Refined by Claude Code</strong>: rewritten around a small stub subclass (the class is abstract now); kept Jonathan's "How to Larp" fixtures and test names; added a bad-input test for each constructor check and setter (null, blank, over 100 characters, old value kept), `matches`, `summary`, `hashCode`, and `equals` for unassigned items and copies. | Microsoft Copilot for the original; Claude Code for the revision. |
| Book | <strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><strong>Zayd Pathan</strong>: wrote the getters and setters (git history, commits "locked in" and "3r").<br><br><strong>Refined by Claude Code</strong>: removed the no-argument constructor; added validation to the constructor and every setter (String descriptors non-blank, `pageCount` greater than 0); added `getType`, `searchText` and `toString`; added the optional ISBN (`getIsbn`, `hasIsbn`, `setIsbn`, `isValidIsbn` using the ISBN-10 and ISBN-13 check-digit rules). | Microsoft Copilot for the boilerplate; Claude Code for the revision (stretch goal 1). |
| BookTest | <strong>Zayd Pathan</strong>: wrote the eight getter and setter tests (git history).<br><br><strong>Refined by Claude Code</strong>: kept Zayd's tests unchanged and added constructor normal and bad-input tests, a bad-input test for every setter (old value kept), `getType`, `toString`, `matches`, and the ISBN tests (valid ISBN-13, ISBN-10 and ISBN-10 ending in X; wrong check digit, wrong length, letters, empty, null). | Claude Code for the added tests. |
| Magazine | <strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><br><strong>Refined by Claude Code</strong>: removed the no-argument constructor; added validation to the constructor and setters; added `getType`, `searchText`, `toString`; added the optional validated date of issue (`getDateOfIssue`, `setDateOfIssue` as `LocalDate`, `isValidDate`). | Microsoft Copilot for the boilerplate; Claude Code for the revision (stretch goal 1). |
| MagazineTest | <strong>Zayd Pathan</strong>: wrote the six getter and setter tests (git history).<br><br><strong>Refined by Claude Code</strong>: kept Zayd's tests and added constructor, setter bad-input, `getType`, `toString`, `matches`, and date tests (real date, leap day, February 30, month 13, wrong format, empty, null; old date kept). | Claude Code for the added tests. |
| DVD | <strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><br><strong>Refined by Claude Code</strong>: removed the no-argument constructor; added validation (genre non-blank, `numEpisodes` greater than 0); added `getType`, `searchText`, `toString`; added JavaDocs. | Microsoft Copilot for the boilerplate; Claude Code for the revision. |
| DVDTest | <strong>Zayd Pathan</strong>: wrote the four getter and setter tests (git history).<br><br><strong>Refined by Claude Code</strong>: kept Zayd's tests and added constructor, setter bad-input, `getType`, `toString`, and `matches` tests. | Claude Code for the added tests. |
| Device | <strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><br><strong>Refined by Claude Code</strong>: removed the no-argument constructor; added validation (all four descriptors non-blank); added `getType`, `searchText`, `toString`; added JavaDocs. | Microsoft Copilot for the boilerplate; Claude Code for the revision. |
| DeviceTest | <strong>Zayd Pathan</strong>: wrote the eight getter and setter tests (git history).<br><br><strong>Refined by Claude Code</strong>: kept Zayd's tests and added constructor, setter bad-input, `getType`, `toString`, and `matches` tests. | Claude Code for the added tests. |
| Game | <strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><br><strong>Refined by Claude Code</strong>: removed the no-argument constructor; added validation (genre, platform, rating non-blank); added `getType`, `searchText`, `toString`; added JavaDocs. | Microsoft Copilot for the boilerplate; Claude Code for the revision. |
| GameTest | <strong>Zayd Pathan</strong>: wrote the six getter and setter tests (git history).<br><br><strong>Refined by Claude Code</strong>: kept Zayd's tests and added constructor, setter bad-input, `getType`, `toString`, and `matches` tests. | Claude Code for the added tests. |
| CheckoutRecord | <strong>Ajeet Bondugula</strong>: added borrower and item validation in the constructor and setters, `getSerial`, `equals`/`hashCode` by serial, and `toString` (original build also had unique chronological IDs).<br><strong>Jonathan Ganun</strong>: created the boilerplate using AI.<br><br><strong>Refined by Claude Code</strong>: followed Revised Scope 3 and 4: removed the blank constructor, the `id`, `getId`, `getItem` and `setItem`; the record now holds `final int serial` (taken from the item, which must already be in a Catalogue) and `borrower`; `equals` and `hashCode` use the serial; `toString` now reads "Item 3 is checked out to Sam."; made the borrower check a package-private `validateBorrower` shared with `Catalogue` and `WaitlistEntry`. | Drafted from the spec's CheckoutRecord table, Invariants, and Where Validation Lives sections; reviewed by Ajeet. Boilerplate: Microsoft Copilot. Revision: Claude Code. |
| CheckoutRecordTest | <strong>Ajeet Bondugula</strong>: wrote and tested 17 tests, with a normal case and a bad-input case for every public method, plus tests that store CheckoutRecords in `LinkedChain<CheckoutRecord>` (Gen AI assisted).<br><br><strong>Refined by Claude Code</strong>: kept Ajeet's structure, names and the two LinkedChain tests; removed the tests of the deleted blank constructor, `getId` and `getItem`; fixtures now add items to a `Catalogue` first so they have serials; added the case of an item with no serial. | Normal and bad-input cases were refined via Claude Code. |
| WaitlistEntry | <strong>Written by Claude Code</strong>: new class for stretch goal 4 (waitlist): a `final int serial` and a `borrower` validated like `CheckoutRecord`'s; `equals` by serial and borrower; `hashCode`; `toString`. Owner: Ajeet (reviewed). | Claude Code, following `CheckoutRecord`'s design. |
| WaitlistEntryTest | <strong>Written by Claude Code</strong>: a normal and a bad-input case for every public method. | Claude Code. |
| Catalogue | <strong>Jacob Mapa</strong>: wrote the original methods (git history: "Implemented methods for Catalogue").<br><strong>Jonathan Ganun</strong>: fixed `getItemsByTitle` and `getItemsByCreator`; added `getAllItems`, `getAllReturnedItems`, `getAllBorrowedItems`, `getAllCheckoutRecords`.<br><br><strong>Refined by Claude Code</strong>: rewrote the class to match the spec: Catalogue now assigns serials from `nextSerial` and `addItem` returns the serial; `borrowItem`, `returnItem` and `removeItem` throw `IllegalStateException` with the messages in the bad-input table (and `removeItem` no longer quietly returns an item); every search copies a chain once with `toArray` so it is O(n) instead of O(n²); fixed the `ClassCastException` and `ArrayList` use in `getAllReturnedItems` and `getAllBorrowedItems`; removed `isValidID` and `isValidSerial`; added `searchItems`, `isCheckedOut`, `getBorrower`, the waitlist (`joinWaitlist`, `getWaitlist`, `getNextInLine`, and a hold for the first person in line), and a third chain. | Claude Code, from the spec, the scope, and the Big-O estimates. Jacob's original and Jonathan's additions were the base. |
| CatalogueTest | <strong>Jacob Mapa</strong>: wrote the original nine tests (git history).<br><br><strong>Refined by Claude Code</strong>: rewritten (42 tests) with items of five types; a normal and a bad-input case for every public method; added tests for an empty catalogue, removing an item that is not there, removing the first, middle and last item, duplicate copies with the same title, serial rules (never reused), the categories, searches, checked-out items that cannot be removed, and the waitlist. | Claude Code for the rewrite. |
| ConsoleMenu | <strong>Written by Claude Code</strong>: new class following the spec's ConsoleMenu design (the only class that reads or prints; takes a `Scanner` and `PrintStream`; `readNonBlank`, `readInt`, `readLine` with end-of-input handling; `run()` wraps every command). The list headers and layouts were adapted from Jonathan's `Main`. Owner: Jonathan (reviewed). | Claude Code, from the spec's Where Validation Lives and the scope's bad-input list. |
| ConsoleMenuTest | <strong>Written by Claude Code</strong> (39 tests): types scripts of lines into the menu and checks the output for every bad-input case in the scope. | Claude Code. |
| Main | <strong>Jonathan Ganun</strong>: wrote the first `Main`, the sample catalogue (15 items of all five types, 5 checked out), and the list-display methods.<br><br><strong>Refined by Claude Code</strong>: `Main` now builds the sample catalogue and hands it to `ConsoleMenu` (moved the loop and display code there, deleted the disabled `running = false` stub); `createSampleCatalogue` is public so it can be tested; gave the Hobbit an ISBN and National Geographic a date of issue. | Claude Code. |
| MainTest | <strong>Written by Claude Code</strong>: tests the sample catalogue (five item types in one chain) and that `Main` starts, shows the menu, and exits on input or on none. | Claude Code. |

## How we used GenAI

* **Before this revision:** Jonathan used **Microsoft Copilot** to generate boilerplate (and JavaDocs) for `LibraryItem`, `CheckoutRecord` and the five subclasses, and to debug tests. Ajeet used **Claude** to draft parts of the specification (Generic Contract, Revised Scope, Where Validation Lives) and **Claude Code** to refine the `CheckoutRecord` tests.
* **This revision (marked "Refined by Claude Code" above):** after the teammates' first build and Jonathan's work on `Main` were pushed, Ajeet asked **Claude Code** (Claude Opus 5.5, then Sonnet 5.5) to grade the project against the Deliverable 3 rubric, compare it with our scope and specification, and fix what it found. It rewrote or extended the classes and tests listed in the table, wrote the new classes (`ConsoleMenu`, `WaitlistEntry`) and test files, and wrote this README. It ran the full test suite (236 tests pass) and the program. It did **not** commit anything: the changes are in the working tree for the group to review.
* **How we checked it:** every class and test in the table was run, and the group should read each file it owns before the grading session. The decisions that were GenAI's call and need group agreement: following the spec's convention over its method table for `borrowItem` and `returnItem`; choosing waitlist and extra descriptors as the committed stretch goals, due dates as the next one, and dropping save and load; and the owner assignments in Responsibilities.
