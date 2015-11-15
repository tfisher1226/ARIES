
Defining a transaction

A transaction is a unit of work that encapsulates multiple database actions such that that either all the encapsulated actions fail or all succeed.

Transactions ensure data integrity when an application interacts with multiple datasources.
Practical Example

If you subscribe to a newspaper using a credit card, you are using a transactional system. Multiple systems are involved, and each of the systems needs the ability to roll back its work, and cause the entire transaction to roll back if necessary. For instance, if the newspaper's subscription system goes offline halfway through your transaction, you don't want your credit card to be charged. If the credit card is over its limit, the newspaper doesn't want your subscription to go through. In either of these cases, the entire transaction should fail of any part of it fails. Neither you as the customer, nor the newspaper, nor the credit card processor, wants an unpredictable (indeterminate) outcome to the transaction.

This ability to roll back an operation if any part of it fails is what JBoss Transactions is all about. This guide assists you in writing transactional applications to protect your data.

"Transactions" in this guide refers to atomic transactions, and embody the "all-or-nothing" concept outlined above. Transactions are used to guarantee the consistency of data in the presence of failures. Transactions fulfill the requirements of ACID: Atomicity, Consistency, Isolation, Durability.

ACID Properties

Atomicity

    The transaction completes successfully (commits) or if it fails (aborts) all of its effects are undone (rolled back). 
Consistency

    Transactions produce consistent results and preserve application specific invariants. 
Isolation

    Intermediate states produced while a transaction is executing are not visible to others. Furthermore transactions appear to execute serially, even if they are actually executed concurrently. 
Durability

    The effects of a committed transaction are never lost (except by a catastrophic failure). 

A transaction can be terminated in two ways: committed or aborted (rolled back). When a transaction is committed, all changes made within it are made durable (forced on to stable storage, e.g., disk). When a transaction is aborted, all of the changes are undone. Atomic actions can also be nested; the effects of a nested action are provisional upon the commit/abort of the outermost (top-level) atomic action. 




Commit protocol

A two-phase commit protocol guarantees that all of the transaction participants either commit or abort any changes made. Figure 1.1, “Two-Phase Commit” illustrates the main aspects of the commit protocol.

Procedure 1.1. Two-phase commit protocol

    During phase 1, the action coordinator, C, attempts to communicate with all of the action participants, A and B, to determine whether they will commit or abort.

    An abort reply from any participant acts as a veto, causing the entire action to abort.

    Based upon these (lack of) responses, the coordinator chooses to commit or abort the action.

    If the action will commit, the coordinator records this decision on stable storage, and the protocol enters phase 2, where the coordinator forces the participants to carry out the decision. The coordinator also informs the participants if the action aborts.

    When each participant receives the coordinator’s phase-one message, it records sufficient information on stable storage to either commit or abort changes made during the action.

    After returning the phase-one response, each participant who returned a commit response must remain blocked until it has received the coordinator’s phase-two message.

    Until they receive this message, these resources are unavailable for use by other actions. If the coordinator fails before delivery of this message, these resources remain blocked. However, if crashed machines eventually recover, crash recovery mechanisms can be employed to unblock the protocol and terminate the action. 



Transactional proxies

The action coordinator maintains a transaction context where resources taking part in the action need to be registered. Resources must obey the transaction commit protocol to guarantee ACID properties. Typically, the resource provides specific operations which the action can invoke during the commit/abort protocol. However, some resources may not be able to be transactional in this way. This may happen if you have legacy code which cannot be modified. Transactional proxies allow you to use these anomalous resources within an action.

The proxy is registered with, and manipulated by, the action as though it were a transactional resource, and the proxy performs implementation specific work to make the resource it represents transactional. The proxy must participate within the commit and abort protocols. Because the work of the proxy is performed as part of the action, it is guaranteed to be completed or undone despite failures of the action coordinator or action participants. 



Nested transactions
http://jbossts.blogspot.com/2009/03/nested-transaction-support.html
http://jbossts.blogspot.com/2010/05/nested-transactions.html
http://jbossts.blogspot.co.uk/2011/10/nested-transactions-101.html


Nested transactions have been around for quite some time actually pre-dating Java - it was ported over from C++. So you'd think people would have gotten the hang of it by now, but not so. Nested transactions are still barely understood and even less used.  Many people use the term 'nested transactions' to mean different things. A true nested transaction is used mainly for fault isolation of specific tasks within a wider transaction.

If you check out most of the papers, documents or presentations that have been written on the subject over the years they'll all tell you that nested transactions (subtransactions) are good because:

    fault-isolation: if subtransaction rolls back (e.g., because an object it was using fails) then this does not require the enclosing transaction to rollback, thus undoing all of the work performed so far.

    modularity: if there is already a transaction associated with a call when a new transaction is begun, then the transaction will be nested within it. Therefore, a programmer who knows that an object require transactions can use them within the object: if the object’s methods are invoked without a client transaction, then the object’s transactions will simply be top-level; otherwise, they will be nested within the scope of the client’s transactions. Likewise, a client need not know that the object is transactional, and can begin its own transaction.


Given a system that provides transactions for certain operations, you can combine them to form another operation, which is also required to be a transaction. The resulting transaction’s effects are a combination of the effects of its constituent transactions. This paradigm creates the concept of nested subtransactions, and the resulting combined transaction is called the enclosing transaction. The enclosing transaction is sometimes referred to as the parent of a nested (or child) transaction. It can also be viewed as a hierarchical relationship, with a top-level transaction consisting of several subordinate transactions.

An important difference exists between nested and top-level transactions.

The effect of a nested transaction is provisional upon the commit/roll back of its enclosing transactions. The effects are recovered if the enclosing transaction aborts, even if the nested transaction has committed.

Subtransactions are a useful mechanism for two reasons:

fault-isolation

    If a subtransaction rolls back, perhaps because an object it is using fails, the enclosing transaction does not need to roll back. 
modularity

    If a transaction is already associated with a call when a new transaction begins, the new transaction is nested within it. Therefore, if you know that an object requires transactions, you can them within the object. If the object’s methods are invoked without a client transaction, then the object’s transactions are top-level. Otherwise, they are nested within the scope of the client's transactions. Likewise, a client does not need to know whether an object is transactional. It can begin its own transaction. 




Messaging/Database race conditions
http://jbossts.blogspot.com/2011/04/messagingdatabase-race-conditions.html

Nothing in the XA or JTA specifications defines the order in which XAResources are processed. The commit calls to the database and message queue may be issued in either order, or even in parallel.

Therefore it is inevitable that every once in a while, probably on the least convenient occasions, the database read in methodB will fail as the data has not yet been released from the previous transaction.

So, having been let down by shortcomings in the specifications, what can the intrepid business logic programmer do to save the day?

