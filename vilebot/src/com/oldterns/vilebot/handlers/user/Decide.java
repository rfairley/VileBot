/**
 * Copyright (C) 2013 Oldterns
 *
 * This file may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.oldterns.vilebot.handlers.user;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.engio.mbassy.listener.Handler;
import ca.szc.keratin.bot.annotation.HandlerContainer;
import ca.szc.keratin.core.event.message.recieve.ReceivePrivmsg;

@HandlerContainer
public class Decide
{
    private static final Pattern choicePattern = Pattern.compile( "(?:(.+?)(?:\\s*\\|\\s*|\\s*$))" );

    private static final Pattern decidePattern = Pattern.compile( "!decide (" + choicePattern + "+)" );

    private static final Random random = new Random();

    @Handler
    private void decideOMatic( ReceivePrivmsg event )
    {
        String text = event.getText();
        Matcher matcher = decidePattern.matcher( text );

        if ( matcher.matches() )
        {
            String choicesBlob = matcher.group( 1 );

            // Process choices blob
            List<String> choices = new LinkedList<String>();
            Matcher choiceMatcher = choicePattern.matcher( choicesBlob );
            while ( choiceMatcher.find() )
            {
                choices.add( choiceMatcher.group( 1 ) );
            }

            StringBuilder sb = new StringBuilder();

            if ( choices.size() == 1 )
            {
                sb.append( "If you already know what you want to do, what are you bugging me for? You should " );
            }
            else
            {
                sb.append( "I think you should " );
            }

            int selection = random.nextInt( choices.size() );
            sb.append( choices.get( selection ).trim() );

            sb.append( ". Go do that now." );

            event.reply( sb.toString() );
        }
    }
}
