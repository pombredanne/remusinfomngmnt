/**
 * Copyright (c) 2004-2007, David A. Czarnecki
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the "David A. Czarnecki" nor the names of
 * its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package del.icio.us.beans;

/**
 * Subscription
 *
 * @author David Czarnecki
 * @version $Id: Subscription.java,v 1.4 2007/01/19 00:14:43 czarneckid Exp $
 * @since 1.0
 */
public class Subscription {

    private String tag;
    private String user;

    /**
     * Construct a new subscription
     *
     * @param tag Tag
     * @param user Username
     */
    public Subscription(String tag, String user) {
        this.tag = tag;
        this.user = user;
    }

    /**
     * Get tag for subscription
     *
     * @return Tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Set tag for subscription
     *
     * @param tag Tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Get user for subscription
     *
     * @return User
     */
    public String getUser() {
        return user;
    }

    /**
     * Set user for subscription
     *
     * @param user User
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Object as tag:user string
     *
     * @return tag:user
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(tag).append(":").append(user);

        return result.toString();
    }
}