/*
 Copyright 2015 Red Hat, Inc. and/or its affiliates.

 This file is part of lightblue.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redhat.lightblue.common.ldap;

/**
 * Error codes specific to LDAP
 *
 * @author dcrissman
 */
public final class LdapErrorCode {

    /** An unsupported feature was used. */
    public static final String ERR_UNSUPPORTED_FEATURE = "ldap:UnsupportedFeature:";

    /** The unique attribute (aka. entityInfo uniqueattr) was not also defined in the schema fields. */
    public static final String ERR_UNDEFINED_UNIQUE_ATTRIBUTE = "ldap:UndefinedUniqueAttribute";

    /** The LDAP request failed. */
    public static final String ERR_LDAP_REQUEST_FAILED = "ldap:RequestFailed";

    /** LDAP returned an unsuccessful response code. */
    public static final String ERR_LDAP_UNSUCCESSFUL_RESPONSE = "ldap:UnsuccessfulResponse";

    public static final String ERR_LDAP_SAVE_ERROR_INS_WITH_NO_UPSERT = "ldap:SaveError:InsertionAttemptWithNoUpsert";

    private LdapErrorCode(){}

}
