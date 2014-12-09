package org.xlet.strawberry.core.jackson;

import org.xlet.strawberry.core.member.TempMember;
import org.xlet.strawberry.core.message.forward.ForwardReadyMessage;
import org.xlet.strawberry.core.message.forward.ForwardRequestMessage;
import org.xlet.strawberry.core.message.forward.ForwardResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.xlet.strawberry.core.message.client.*;
import org.xlet.strawberry.core.message.server.*;

import java.util.Collection;

/**
 * mapper creator.
 */
public class MapperCreator {

    private Collection<NamedSubType> subTypes;

    private ObjectMapper mapper;

    public ObjectMapper mapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();

            // register core defined sub types.
            this.registerMessageSubType();
            this.registerMemberSubType();

            for (NamedSubType namedSubType : subTypes) {
                NamedType namedType = new NamedType(namedSubType.getType(), namedSubType.getName());
                mapper.registerSubtypes(namedType);
            }
        }
        return mapper;
    }

    private void registerMemberSubType() {
        mapper.registerSubtypes(
                new NamedType(TempMember.class, "TEMP")
        );
    }

    public void registerMessageSubType() {
        mapper.registerSubtypes(
                //client message register.
                new NamedType(ConnectMessage.class, "Connect"),
                new NamedType(ConnectResponseMessage.class, "ConnectResponse"),
                new NamedType(FriendGroupMessage.class, "FriendGroup"),
                new NamedType(LoginMessage.class, "Login"),
                new NamedType(LoginResponseMessage.class, "LoginResponse"),
                new NamedType(LogoutMessage.class, "Logout"),
                new NamedType(MemberStatusMessage.class, "Status"),
                new NamedType(NormalMessage.class, "Normal"),
                new NamedType(OfflineMessage.class, "Offline"),
                new NamedType(RecentContactsMessage.class, "RecentContacts"),
                //forward message register.
                new NamedType(ForwardReadyMessage.class, "ForwardReady"),
                new NamedType(ForwardRequestMessage.class, "ForwardRequest"),
                new NamedType(ForwardResponseMessage.class, "ForwardResponse"),
                //server message register.
                new NamedType(MemberLogoutMessage.class, "MemberLogout"),
                new NamedType(ConnectedMessage.class, "Connected"),
                new NamedType(ConnectedResponseMessage.class, "ConnectedResponse"),
                new NamedType(ForwardMessage.class, "Forward"),
                new NamedType(ReadyMessage.class, "Ready"),
                new NamedType(ServerRegisterMessage.class, "ServerRegister"),
                new NamedType(ServerRegisterResponseMessage.class, "ServerRegisterResponse"),
                new NamedType(TokenMessage.class, "Token"),
                new NamedType(TokenResponseMessage.class, "TokenResponse")

        );
    }

    public void setSubTypes(Collection<NamedSubType> subTypes) {
        this.subTypes = subTypes;
    }
}
