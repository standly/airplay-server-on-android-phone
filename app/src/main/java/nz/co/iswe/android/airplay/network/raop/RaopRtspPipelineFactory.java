/*
 * This file is part of AirReceiver.
 *
 * AirReceiver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * AirReceiver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with AirReceiver.  If not, see <http://www.gnu.org/licenses/>.
 */

package nz.co.iswe.android.airplay.network.raop;

import nz.co.iswe.android.airplay.AirPlayServer;
import nz.co.iswe.android.airplay.audio.RaopAudioHandler;
import nz.co.iswe.android.airplay.network.ExceptionLoggingHandler;
import nz.co.iswe.android.airplay.network.NetworkUtils;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.rtsp.RtspRequestDecoder;
import org.jboss.netty.handler.codec.rtsp.RtspResponseEncoder;
import org.phlo.AirReceiver.RaopRtspChallengeResponseHandler;
import org.phlo.AirReceiver.RaopRtspHeaderHandler;
import org.phlo.AirReceiver.RaopRtspOptionsHandler;
import org.phlo.AirReceiver.RtspErrorResponseHandler;
import org.phlo.AirReceiver.RtspLoggingHandler;
import org.phlo.AirReceiver.RtspUnsupportedResponseHandler;

/**
 * Factory for AirTunes/RAOP RTSP channels
 */
public class RaopRtspPipelineFactory implements ChannelPipelineFactory {
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		final ChannelPipeline pipeline = Channels.pipeline();

		final AirPlayServer airPlayServer = AirPlayServer.getIstance();
		
		pipeline.addLast("executionHandler", airPlayServer.getChannelExecutionHandler());
		pipeline.addLast("closeOnShutdownHandler", new SimpleChannelUpstreamHandler() {
			@Override
			public void channelOpen(final ChannelHandlerContext ctx, final ChannelStateEvent e) throws Exception {
				airPlayServer.getChannelGroup().add(e.getChannel());
				super.channelOpen(ctx, e);
			}
		});
		pipeline.addLast("exceptionLogger", new ExceptionLoggingHandler());
		pipeline.addLast("decoder", new RtspRequestDecoder());
		pipeline.addLast("encoder", new RtspResponseEncoder());
		pipeline.addLast("logger", new RtspLoggingHandler());
		pipeline.addLast("errorResponse", new RtspErrorResponseHandler());
		pipeline.addLast("challengeResponse", new RaopRtspChallengeResponseHandler(NetworkUtils.getInstance().getHardwareAddress()));
		pipeline.addLast("header", new RaopRtspHeaderHandler());
		pipeline.addLast("options", new RaopRtspOptionsHandler());
		pipeline.addLast("audio", new RaopAudioHandler(airPlayServer.getExecutorService()));
		pipeline.addLast("unsupportedResponse", new RtspUnsupportedResponseHandler());

		return pipeline;
	}

}
