/**
 * This module exports the SpectatorController class constructor.
 * 
 * This component does...
 */
define(function(require){
  'use strict';
  
  // imports
  var StatePatternMixin = require('../../util/StatePatternMixin');
  var ControlsToolbarMixin = require('../../util/ControlsToolbarMixin');
  var SpectatorModeConstants = require('./SpectatorModeConstants');
  
  // import SPECTATOR mode states
  var SpectatorModeStartState = require('./SpectatorModeStartState');

  /**
   * Constructor function.
   */
  function SpectatorController() {
    // Add the StatePattern mixin
    StatePatternMixin.call(this);
    
    // create states and a lookup map
      this.addStateDefinition(SpectatorModeConstants.SPECTATOR_MODE_STARTING,
          new SpectatorModeStartState(this));
      this.addStateDefinition(SpectatorModeConstants.QUIT_SPEC_BUTTON,
          new SpectatorModeStartState(this));
    
    // Add the ModeControls mixin
    ControlsToolbarMixin.call(this);

    this.addButton(SpectatorModeConstants.QUIT_SPEC_BUTTON, 'Leave Viewing', true,
        SpectatorModeConstants.QUIT_SPEC_BUTTON, this.leave);

    // Public (internal) methods

    /**
     * Start Spectator mode.
     */
    this.startup = function startup() {
      // start Spectator mode
      this.setState(SpectatorModeConstants.SPECTATOR_MODE_STARTING);
    }
    
  };



  //
  // Public (external) methods
  //

    /**
     * Resign from the game.
     *
     * This action leaves the current Game view and retrieves an
     * updated Game view from the server.
     */
    SpectatorController.prototype.leave = function leave() {
        // confirm that the player really wants to resign
        var yes = window.confirm('Are you sure you want to leave viewing?');
        if (!yes) {
            // if not, the return
            this.displayMessage( {type: 'info', text: 'leave viewing cancelled.'} );
            return;
        }

        // if confirmed, then send the resignation command to the server
        jQuery.post('/leave', '')
        // HTTP success handler
            .done(handleResponse.bind(this))
            // HTTP error handler
            .fail(AjaxUtils.handleErrorResponse)
            // always display a message that the Ajax call has completed.
            .always(() => console.debug('leaveSpectating response complete.'));

        //
        function handleResponse(message) {
            if (message.type === 'info') {
                // tell the browser to route the player to the Home page
                window.location = '/';
            }
            // handle error message
            else {
                this.displayMessage(message);
            }
        }
    }

  // export class constructor
  return SpectatorController;

});
