package org.dew.portlet;

import java.io.Serializable;

public 
class WActionResult implements Serializable
{
	private static final long serialVersionUID = -9221372151693955427L;
	
	protected String action;
	protected IAction actionHandler;
	protected Object actionResult;
	protected Exception exception;
	
	protected long time;
	
	public WActionResult()
	{
	}
	
	public WActionResult(String action, IAction actionHandler, Object actionResult)
	{
		this.action = action;
		this.actionHandler = actionHandler;
		this.actionResult = actionResult;
		
		this.time = System.currentTimeMillis();
	}
	
	public WActionResult(String action, IAction actionHandler, Throwable throwable)
	{
		this.action = action;
		this.actionHandler = actionHandler;
		if(throwable instanceof Exception) {
			this.exception = (Exception) throwable;
		}
		else if(throwable != null) {
			this.exception = new Exception(throwable);
		}
		
		this.time = System.currentTimeMillis();
	}
	
	public Object getActionResult() {
		return actionResult;
	}
	
	public void setActionResult(Object actionResult) {
		this.actionResult = actionResult;
		this.exception = null;
		this.time = System.currentTimeMillis();
	}
	
	public IAction getActionHandler() {
		return actionHandler;
	}
	
	public void setActionHandler(IAction actionHandler) {
		this.actionHandler = actionHandler;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception exception) {
		this.actionResult = null;
		this.exception = exception;
		this.time = System.currentTimeMillis();
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean isException() {
		return this.exception != null;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof WActionResult) {
			String sAction = ((WActionResult) object).getAction();
			long   lTime   = ((WActionResult) object).getTime();
			String objeActionTime = sAction     + ":" + lTime;
			String thisActionTime = this.action + ":" + this.time;
			return thisActionTime.equals(objeActionTime);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (action + ":" + time).hashCode();
	}
	
	@Override
	public String toString() {
		return "WActionResult(" + action + "," + time + ")";
	}
}

