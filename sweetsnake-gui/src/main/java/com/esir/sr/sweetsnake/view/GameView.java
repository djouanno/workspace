package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.dto.ElementDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.uicomponent.GameBoardPanel;
import com.esir.sr.sweetsnake.uicomponent.Snake;
import com.esir.sr.sweetsnake.uicomponent.Sweet;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class GameView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 6919247419837806743L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(GameView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game board DTO */
    private GameBoardDTO        gameBoardDto;

    /** The game board panel */
    private GameBoardPanel      gameBoardPL;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected GameView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#init()
     */
    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.info("Initializing a new SweetSnakeGameView");
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#buildImpl()
     */
    @Override
    public void buildImpl() {
        setLayout(new BorderLayout());

        initGameBoardPL();
        add(gameBoardPL, BorderLayout.CENTER);
    }

    /**
     * 
     * @param _gameBoardDto
     */
    public void setGameBoardDto(final GameBoardDTO _gameBoardDto) {
        gameBoardDto = _gameBoardDto;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initGameBoardPL() {
        if (gameBoardDto != null) {
            gameBoardPL = new GameBoardPanel(gameBoardDto.getWidth(), gameBoardDto.getHeight());
            for (int x = 0; x < gameBoardDto.getWidth(); x++) {
                for (int y = 0; y < gameBoardDto.getHeight(); y++) {
                    final ElementDTO elementDto = gameBoardDto.getElement(x, y);
                    if (elementDto != null) {
                        IElement element = null;
                        switch (elementDto.getType()) {
                            case SNAKE:
                                element = new Snake(elementDto.getId(), x, y);
                                break;
                            case SWEET:
                                element = new Sweet(elementDto.getId(), x, y);
                                break;
                            default:
                                break;
                        }
                        gameBoardPL.setElement(element);
                    }
                }
            }
        }
    }

}
