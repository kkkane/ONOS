package org.onosproject.hello;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.Host;
import org.onosproject.net.HostId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.flow.DefaultTrafficSelector;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.host.HostService;
//import org.onosproject.net.intent.HostToHostIntent;
import org.onosproject.net.intent.IntentService;
import org.onosproject.net.packet.DefaultOutboundPacket;
import org.onosproject.net.packet.InboundPacket;
import org.onosproject.net.packet.OutboundPacket;
import org.onosproject.net.packet.PacketContext;
import org.onosproject.net.packet.PacketPriority;
import org.onosproject.net.packet.PacketProcessor;
import org.onosproject.net.packet.PacketService;
import org.onosproject.net.topology.TopologyService;
import org.onlab.packet.Ethernet;
//import org.onlab.packet.IpAddress;
import org.onlab.packet.Ip4Address;
import org.onlab.packet.Ip4Prefix;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
/**
* Sample reactive forwarding application.
*/
@Component(immediate = true)
public class Hello {
    // for verbose output
    private final Logger log = getLogger(getClass());
    // a list of our dependencies :
    // to register with ONOS as an application - described next
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;
    // topology information
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected TopologyService topologyService;
    // to receive Packet-in events that we'll respond to
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected PacketService packetService;
    // to submit/withdraw intents for traffic manipulation
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected IntentService intentService;
    // end host information
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected HostService hostService;
    // our application-specific event handler
    private ReactivePacketProcessor processor = new ReactivePacketProcessor();
    // our unique identifier
    private ApplicationId appId;
    // method called at startup
    @Activate
    public void activate() {
        // "org.onlab.onos.ifwd" is the FQDN of our app
        appId = coreService.registerApplication("org.onosproject.hello");
        // register our event handler
        packetService.addProcessor(processor, PacketProcessor.ADVISOR_MAX + 2);
        TrafficSelector.Builder selector = DefaultTrafficSelector.builder();

        //set match:ARP, IPV4 action:controller
        /*selector.matchEthType(Ethernet.TYPE_ARP);
        packetService.requestPackets(selector.build(), PacketPriority.REACTIVE, appId);
        selector.matchEthType(Ethernet.TYPE_IPV4);
        packetService.requestPackets(selector.build(), PacketPriority.REACTIVE, appId);*/

        defaultRuleForSW();

        log.info("SSSSSSSSSSSSSSSSSStarteddddddddddddd");
        //System.out.println("SSSSSSSSSSSSSSSSSstarteddddddddddddd");
    }
    // method called at shutdown
    @Deactivate
    public void deactivate() {
        // deregister and null our handler
        packetService.removeProcessor(processor);
        processor = null;
        log.info("SSSSSSSSStoppedddddddddddddd");
        //System.out.println("SSSSSSSSSSStoppeddddddddddddd");
    }
    // our handler defined as a private inner class
    /**
    * Packet processor responsible for forwarding packets along their paths.
    */
    private class ReactivePacketProcessor implements PacketProcessor {
        @Override
        public void process(PacketContext context) {
            // Stop processing if the packet has been handled, since we
            // can't do any more to it.
            //System.out.println("Hello a!");
            if (context.isHandled()) {
                return;
            }
            //System.out.println("Hello b!");
            // Extract the original Ethernet frame from the packet information
            InboundPacket pkt = context.inPacket();
            Ethernet ethPkt = pkt.parsed();
            // Find and source and destination hosts
            HostId srcId = HostId.hostId(ethPkt.getSourceMAC());
            HostId dstId = HostId.hostId(ethPkt.getDestinationMAC());
            packetInProcess(pkt);
        }
    }
    // Sends a packet out the specified port.
    private void packetOut(PacketContext context, PortNumber portNumber) {
        context.treatmentBuilder().setOutput(portNumber);
        context.send();
        //log.info("A. sending setOutput");
    }
    private void forwardPacketToDst(PacketContext context, Host dst) {
        TrafficTreatment treatment = DefaultTrafficTreatment.builder().setOutput(dst.location().port()).build();
        OutboundPacket packet = new DefaultOutboundPacket(dst.location().deviceId(),
        treatment, context.inPacket().unparsed());
        log.info("D. DevicId {}", dst.location().deviceId());
        packetService.emit(packet);
        log.info("B. sending packet: {}", packet);
        //System.out.print("sendinggggg packet: ");
        //System.out.println(packet);
    }
    private void defaultRuleForSW() {
        TrafficSelector.Builder selectorBuilder = DefaultTrafficSelector.builder();
        byte[] host1 = {0x10, 0x0, 0x0, 0x1};
        byte[] host2 = {0x10, 0x0, 0x0, 0x2};
        Ip4Address ip1 = Ip4Address.valueOf(host1);
        Ip4Address ip2 = Ip4Address.valueOf(host2);
        Ip4Prefix matchIp4SrcPrefix1 = Ip4Prefix.valueOf(ip1, Ip4Prefix.MAX_MASK_LENGTH);
        Ip4Prefix matchIp4SrcPrefix2 = Ip4Prefix.valueOf(ip2, Ip4Prefix.MAX_MASK_LENGTH);
        selectorBuilder.matchIPSrc(matchIp4SrcPrefix1);
        packetService.requestPackets(selectorBuilder.build(), PacketPriority.REACTIVE, appId);
        selectorBuilder.matchIPSrc(matchIp4SrcPrefix2);
        packetService.requestPackets(selectorBuilder.build(), PacketPriority.REACTIVE, appId);
    }
    private void packetInProcess(InboundPacket pkt) {
        byte[] host1 = {0x10, 0x0, 0x0, 0x1};
        byte[] host2 = {0x10, 0x0, 0x0, 0x2};
        Ip4Address ip1 = Ip4Address.valueOf(host1);
        Ip4Address ip2 = Ip4Address.valueOf(host2);
        log.info("Pkt. IP src {}", pkt.receivedFrom().ipElementId().ipAddress().getIp4Address());
        Ip4Address srcIp = pkt.receivedFrom().ipElementId().ipAddress().getIp4Address();
        if (srcIp.equals(ip1)) {
            log.info("Pkt. src {} you are h1", pkt.receivedFrom().ipElementId().ipAddress());
        }
        if (srcIp.equals(ip2)) {
            log.info("Pkt. src {} you are h2", pkt.receivedFrom().ipElementId().ipAddress());
        }
    }
}
