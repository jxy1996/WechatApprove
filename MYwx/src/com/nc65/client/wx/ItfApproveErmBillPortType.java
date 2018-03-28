/**
 * ItfApproveErmBillPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nc65.client.wx;

public interface ItfApproveErmBillPortType extends java.rmi.Remote {
    public java.lang.String[] getPK(java.lang.String string) throws java.rmi.RemoteException;
    public java.lang.String approveErmBill(java.lang.String string, java.lang.String string1) throws java.rmi.RemoteException;
    public java.lang.String needBindings(java.lang.String string, java.lang.String string1) throws java.rmi.RemoteException;
    public java.lang.String hasReject(java.lang.String string, java.lang.String string1) throws java.rmi.RemoteException;
    public java.lang.String needApprove(java.lang.String string, java.lang.String string1) throws java.rmi.RemoteException;
    public java.lang.String hasApprove(java.lang.String string, java.lang.String string1) throws java.rmi.RemoteException;
}
