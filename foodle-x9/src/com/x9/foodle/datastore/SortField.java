package com.x9.foodle.datastore;

public class SortField {
	public final SortableFields field;
	public final Order order;

	public SortField(SortableFields field, Order order) {
		super();
		this.field = field;
		this.order = order;
	}

	/**
	 * Same as {@code SortField(filed, Order.ASC)}.
	 * 
	 * @see SortField#SortField(SortableFields, Order)
	 * @param field
	 */
	public SortField(SortableFields field) {
		this(field, field.defaultOrder);
	}

	public SortField(String paramString) {
		String[] split = paramString.split(":");
		if (split.length != 2) {
			throw new IllegalArgumentException("invalid sortfield parameter: "
					+ paramString);
		}
		this.field = SortableFields.valueOf(split[0]);
		this.order = Order.valueOf(split[1]);
	}

	public String toParamString() {
		return field.name() + ":" + order.name();
	}

	@Override
	public String toString() {
		return "SortField [field=" + field + ", order=" + order + "]";
	}

}
