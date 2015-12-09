package nam.ui.design;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.aries.ui.EventManager;
import org.aries.ui.event.Add;
import org.aries.ui.event.Added;
import org.aries.ui.event.Cancel;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Checked;
import org.aries.ui.event.Clear;
import org.aries.ui.event.Refresh;
import org.aries.ui.event.Refreshed;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Removed;
import org.aries.ui.event.Save;
import org.aries.ui.event.Saved;
import org.aries.ui.event.Select;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Selection;
import org.aries.ui.event.Unchecked;
import org.aries.ui.event.Unselect;
import org.aries.ui.event.Unselected;


@SuppressWarnings("unchecked")
public abstract class AbstractEventManager<T> implements EventManager<T> {

	protected abstract T getInstance();

	@Inject
	@Add
	private Event<T> addEvent;

	@Inject
	@Added
	private Event<T> addedEvent;

	@Inject
	@Save
	private Event<T> saveEvent;

	@Inject
	@Saved
	private Event<T> savedEvent;

	@Inject
	@Remove
	private Event<T> removeEvent;
	
	@Inject
	@Removed
	private Event<T> removedEvent;
	
	@Inject
	@Refresh
	private Event<T> refreshEvent;
	
	@Inject
	@Refreshed
	private Event<T> refreshedEvent;
	
	@Inject
	@Cancel
	private Event<T> cancelEvent;
	
	@Inject
	@Cancelled
	private Event<T> cancelledEvent;
	
	@Inject
	@Select
	private Event<T> selectEvent;
	
	@Inject
	@Selected
	private Event<T> selectedEvent;
	
	@Inject
	@Unselect
	private Event<T> unselectEvent;
	
	@Inject
	@Unselected
	private Event<T> unselectedEvent;
	
	@Inject
	@Checked
	private Event<T> checkedEvent;
	
	@Inject
	@Unchecked
	private Event<T> uncheckedEvent;
	
	@Inject
	@Clear
	@Selection
	private Event<String> clearSelectionEvent;
	

	public void fireAddEvent() {
		fireAddEvent(getInstance());
	}

	public void fireAddEvent(Object object) {
		addEvent.fire((T) object);
	}
	
	public void fireAddedEvent() {
		fireAddedEvent(getInstance());
	}

	public void fireAddedEvent(Object object) {
		addedEvent.fire((T) object);
	}
	
	public void fireSaveEvent() {
		fireSaveEvent(getInstance());
	}

	public void fireSaveEvent(Object object) {
		saveEvent.fire((T) object);
	}
	
	public void fireSavedEvent() {
		fireSavedEvent(getInstance());
	}

	public void fireSavedEvent(Object object) {
		savedEvent.fire((T) object);
	}
	
	public void fireRemoveEvent() {
		fireRemoveEvent(getInstance());
	}

	public void fireRemoveEvent(Object object) {
		removeEvent.fire((T) object);
	}
	
	public void fireRemovedEvent() {
		fireRemovedEvent(getInstance());
	}
	
	public void fireRemovedEvent(Object object) {
		removedEvent.fire((T) object);
	}
	
	public void fireRefreshEvent() {
		fireRefreshEvent(getInstance());
	}

	public void fireRefreshEvent(Object object) {
		refreshEvent.fire((T) object);
	}
	
	public void fireRefreshedEvent() {
		fireRefreshedEvent(getInstance());
	}
	
	public void fireRefreshedEvent(Object object) {
		refreshedEvent.fire((T) object);
	}
	
	public void fireCancelEvent() {
		fireCancelEvent(getInstance());
	}

	public void fireCancelEvent(Object object) {
		cancelEvent.fire((T) object);
	}
	
	public void fireCancelledEvent() {
		fireCancelledEvent(getInstance());
	}

	public void fireCancelledEvent(Object object) {
		cancelledEvent.fire((T) object);
	}

	public void fireSelectEvent() {
		fireSelectEvent(getInstance());
	}

	public void fireSelectEvent(Object object) {
		selectEvent.fire((T) object);
	}
	
	public void fireSelectedEvent() {
		fireSelectedEvent(getInstance());
	}

	public void fireSelectedEvent(Object object) {
		selectedEvent.fire((T) object);
	}
	
	public void fireUnselectEvent() {
		fireSelectEvent(getInstance());
	}

	public void fireUnselectEvent(Object object) {
		selectEvent.fire((T) object);
	}
	
	public void fireUnselectedEvent() {
		fireUnselectedEvent(getInstance());
	}

	public void fireUnselectedEvent(Object object) {
		unselectedEvent.fire((T) object);
	}
	
	public void fireClearSelectionEvent() {
		fireClearSelectionEvent(getClass().getSimpleName());
	}

	public void fireClearSelectionEvent(String source) {
		clearSelectionEvent.fire(source);
	}

	public void fireCheckedEvent() {
		fireCheckedEvent(getInstance());
	}

	public void fireCheckedEvent(Object object) {
		checkedEvent.fire((T) object);
	}
	
	public void fireUncheckedEvent() {
		fireUncheckedEvent(getInstance());
	}

	public void fireUncheckedEvent(Object object) {
		uncheckedEvent.fire((T) object);
	}
	
}
